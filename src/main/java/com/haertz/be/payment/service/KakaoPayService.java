package com.haertz.be.payment.service;

import com.haertz.be.booking.dto.request.PreBookingRequest;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.booking.repository.DesignerScheduleRepository;
import com.haertz.be.booking.service.DesignerScheduleDomainService;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.utils.AuthenticatedUserUtils;
import com.haertz.be.googlemeet.dto.GoogleMeetDto;
import com.haertz.be.googlemeet.service.GoogleMeetService;
import com.haertz.be.payment.dto.*;
import com.haertz.be.payment.entity.Payment;
import com.haertz.be.payment.entity.PaymentMethod;
import com.haertz.be.payment.entity.PaymentStatus;
import com.haertz.be.payment.exception.PaymentErrorCode;
import com.haertz.be.payment.repository.temp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class KakaoPayService {
    private static final String Host = "https://open-api.kakaopay.com/online";
    @Value("${KAKAOADMIN_KEY}")
    private String kakaoAdminKey;
    //프론트가 배포 한 주소로 설정하기, 사용자가 결제완료 버튼을 누르면 ex.approvalurl(성공시)로 리디렉트되고 이때 pg_token이 포함되어있음,이 토큰 받아 api/kakapay/approve로 요청보내면 됨.
    @Value("${APPROVE_URL}")
    private String approveUrl;
    @Value("${CANCEL_URL}")
    private String cancelUrl;
    @Value("${FAIL_URL}")
    private String failUrl;

    private KakaoPayDTO kakaoPayDTO;
    private String cid = "TC0ONETIME"; //가맹점용 코드(테스트용)
    private final RestTemplate restTemplate= new RestTemplate();
    private final PaymentSaveService paymentSaveService;
    private final temp temp;
    private final AuthenticatedUserUtils userUtils;
    private final DesignerScheduleDomainService designerScheduleDomainService;
    private final GoogleMeetService googleMeetService;
    private final DesignerScheduleRepository designerScheduleRepository;

    public KakaoPayDTO kakaoPayReady(KakaoPayRequestDTO requestDTO) {
        Long currentUserId = userUtils.getCurrentUserId();
        //디자이너스케쥴(임시예약정보 생성해 다른 사용자가 예약하지 못하도록
        PreBookingRequest preBooking = new PreBookingRequest(
                requestDTO.getDesignerId(),       // 디자이너 ID
                requestDTO.getBookingDate(),      // 예약 날짜
                requestDTO.getBookingTime()       // 예약 시간
        );
        // 임시 스케줄 생성 후 해당 스케줄의 ID를 반환받음.
        Long tempScheduleId = designerScheduleDomainService.registerTempSchedule(currentUserId, preBooking);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaoAdminKey);
        headers.add("Content-type", "application/json");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("partner_order_id",tempScheduleId);
        parameters.put("partner_user_id",currentUserId);
        parameters.put("item_name", requestDTO.getItem_name());
        parameters.put("quantity", requestDTO.getQuantity());
        parameters.put("total_amount", requestDTO.getTotal_amount());
        parameters.put("tax_free_amount", requestDTO.getTax_free_amount());
        parameters.put("approval_url", approveUrl);
        parameters.put("cancel_url", cancelUrl);
        parameters.put("fail_url", failUrl);
        //헤더와 바디 붙이기
        HttpEntity<Map<String,Object>>body = new HttpEntity<>(parameters, headers);
        try {
            kakaoPayDTO = restTemplate.postForObject(new URI(Host + "/v1/payment/ready"), body, KakaoPayDTO.class);
            log.info("카카오페이 요청 성공:{}}", kakaoPayDTO);
            if (kakaoPayDTO != null) {
                kakaoPayDTO.setDesignerScheduleId(String.valueOf(tempScheduleId));
            }
            return kakaoPayDTO;
        } catch (RestClientException | URISyntaxException e) {
            log.error("카카오페이 요청 실패", e);
            return null;
        }
    }
    public KakaoPayApproveDto kakaoPayApprove(KakaoPayApproveRequestDto requestDTO) {
        Long currentUserId = userUtils.getCurrentUserId();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaoAdminKey);
        headers.add("Content-type", "application/json");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", requestDTO.getTid());
        parameters.put("partner_order_id", requestDTO.getDesignerScheduleId());
        parameters.put("partner_user_id",currentUserId);
        parameters.put("pg_token", requestDTO.getPg_token());

        HttpEntity<Map<String, Object>> body = new HttpEntity<>(parameters, headers);
        try {
            KakaoPayApproveDto approveResponse = restTemplate.postForObject(
                    new URI(Host + "/v1/payment/approve"), body, KakaoPayApproveDto.class);
            log.info("결제 승인 응답: {}", approveResponse);

            //승인 응답에서 amount 정보 가져오기
            BigDecimal totalAmount=new BigDecimal(approveResponse.getAmount().getTotal());
            // 결제 내역 저장위한 dto 설정
            PaymentSaveDto paymentSaveDto = new PaymentSaveDto();
            paymentSaveDto.setPaymentMethod(PaymentMethod.KAKAO_PAY);
            paymentSaveDto.setUserId(currentUserId);
            paymentSaveDto.setPaymentDate(new Date());
            paymentSaveDto.setTotalAmount(totalAmount);
            paymentSaveDto.setPaymentStatus(PaymentStatus.COMPLETED);
            paymentSaveDto.setPaymentTransaction(requestDTO.getTid());
            paymentSaveDto.setPartnerOrderId(requestDTO.getDesignerScheduleId());
            // 결제내역 저장
            Payment savedpayment=paymentSaveService.savePayment(paymentSaveDto);

            // 디자이너 스케줄의 상태를 변경 메서드 호출
            designerScheduleDomainService.confirmScheduleAfterPayment(Long.valueOf(requestDTO.getDesignerScheduleId()), PaymentStatus.COMPLETED);

            // 디자이너 스케줄을 조회해서, 해당 스케줄의 디자이너 ID와 예약 시간으로 구글 미팅 링크 조회
            DesignerSchedule schedule = designerScheduleRepository.findById(
                            Long.valueOf(requestDTO.getDesignerScheduleId()))
                    .orElseThrow(() -> new BaseException(BookingErrorCode.DESIGNER_SCHEDULE_NOT_FOUND));
            GoogleMeetDto meetDto=googleMeetService.getMeetingLink(schedule.getDesignerId(),schedule.getBookingTime());
            String googleMeetlink=meetDto.getGoogleMeetingLink();

            approveResponse.setPaymentId(savedpayment.getPaymentId());
            approveResponse.setPaymentstatus(savedpayment.getPaymentStatus());
            approveResponse.setGoogleMeetingLink(googleMeetlink);
            return approveResponse;
        } catch (RestClientException | URISyntaxException e) {
            log.error("결제 승인 실패", e);
            throw new BaseException(PaymentErrorCode.PAYMENT_PROCESSING_ERROR);
        }
    }
    public KakaoPayCancelDto kakaoPayCancel(KakaoPayCancelRequestDto requestDTO) {
        Long currentUserId = userUtils.getCurrentUserId();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaoAdminKey);
        headers.add("Content-type", "application/json");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", requestDTO.getTid());
        parameters.put("partner_order_id", requestDTO.getPartnerOrderId());
        parameters.put("partner_user_id", currentUserId);
        parameters.put("cancel_amount", requestDTO.getCancelAmount());
        parameters.put("cancel_tax_free_amount", requestDTO.getCancelTaxFreeAmount());

        HttpEntity<Map<String,Object>>body = new HttpEntity<>(parameters, headers);
        try {
            KakaoPayCancelDto cancelResponse = restTemplate.postForObject(
                    new URI(Host + "/v1/payment/cancel"), body, KakaoPayCancelDto.class);
            log.info("결제 취소 응답: {}", cancelResponse);

            String tid = cancelResponse.getTid();
            Payment payment= temp.findByPaymentTransaction(tid)
                    .orElseThrow(() -> new BaseException(PaymentErrorCode.PAYMENT_NOT_FOUND));
            //결제 status 업데이트
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
            temp.save(payment);

            // 디자이너 스케줄 테이블 삭제 함수 호출
            Long designerScheduleId= Long.valueOf(payment.getPartnerOrderId());
            designerScheduleDomainService.deleteScheduleAfterFailedPayment(designerScheduleId);

            KakaoPayCancelDto kakaoPayCancelDto = new KakaoPayCancelDto();
            kakaoPayCancelDto.setCid(cancelResponse.getCid());
            kakaoPayCancelDto.setTid(cancelResponse.getTid());
            kakaoPayCancelDto.setPaymentstatus(payment.getPaymentStatus());

            return kakaoPayCancelDto;
        } catch (RestClientException | URISyntaxException e) {
            log.error("결제 취소 실패", e);
            throw new BaseException(PaymentErrorCode.PAYMENT_CANCELLATION_ERROR);
        }
    }
}
