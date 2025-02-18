package com.haertz.be.payment.service;

import com.haertz.be.common.exception.base.BaseException;
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

    public KakaoPayDTO kakaoPayReady(KakaoPayRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaoAdminKey);
        headers.add("Content-type", "application/json");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("partner_order_id", requestDTO.getPartner_order_id());
        parameters.put("partner_user_id", requestDTO.getPartner_user_id());
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
            return kakaoPayDTO;
        } catch (RestClientException | URISyntaxException e) {
            log.error("카카오페이 요청 실패", e);
            return null;
        }
    }
    public KakaoPayApproveDto kakaoPayApprove(KakaoPayApproveRequestDto requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaoAdminKey);
        headers.add("Content-type", "application/json");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", requestDTO.getTid());
        parameters.put("partner_order_id", requestDTO.getPartner_order_id());
        parameters.put("partner_user_id", requestDTO.getPartner_user_id());
        parameters.put("pg_token", requestDTO.getPg_token());

        HttpEntity<Map<String, Object>> body = new HttpEntity<>(parameters, headers);
        try {
            KakaoPayApproveDto approveResponse = restTemplate.postForObject(
                    new URI(Host + "/v1/payment/approve"), body, KakaoPayApproveDto.class);
            log.info("결제 승인 응답: {}", approveResponse);

            //승인 응답에서 amount 정보 가져오기
            BigDecimal totalAmount=new BigDecimal(approveResponse.getAmount().getTotal());
            //1. 결제 내역 저장위한 dto 설정
            PaymentSaveDto paymentSaveDto = new PaymentSaveDto();
            paymentSaveDto.setPaymentMethod(PaymentMethod.KAKAO_PAY);
            paymentSaveDto.setUserId(Long.valueOf(requestDTO.getPartner_user_id()));
            paymentSaveDto.setPaymentDate(new Date());
            paymentSaveDto.setTotalAmount(totalAmount);
            paymentSaveDto.setPaymentStatus(PaymentStatus.COMPLETED);
            paymentSaveDto.setPaymentTransaction(requestDTO.getTid());
            paymentSaveDto.setPartnerOrderId(requestDTO.getPartner_order_id());
            //2.결제내역 저장
            Payment savedpayment=paymentSaveService.savePayment(paymentSaveDto);

            /*구글 미팅링크 생성 관련 코드들
            GoogleMeetRequestDto googleMeetRequestDto = new GoogleMeetRequestDto();
            googleMeetRequestDto.setReservationId(1L); //현재는 테스트용. 예약엔티티 설정 후 변경.
            googleMeetRequestDto.setUserId(Long.valueOf(requestDTO.getPartner_user_id()));
            googleMeetRequestDto.setGooglemeetaccessToken("ya29.a0AXeO80RuWDPXpOuB--diMWqZ0g2MCyjZol0GiWk9643x9wGR8dwTwfeIJWSefvgdpAR9U8P9jJbCM0VBO-gC4rtstWB3UFUMsDV8AE6okedSaoVNu0BNO8NpxfflqNB6oh3oJW4kHvuJnHPCPly0mZWrY-QU_QhUcLgWd12SrQaCgYKAVoSARISFQHGX2MiLTsc60kevgi5VbtqxCGF_Q0177");
            GoogleMeetDto googleMeetingLink= googleMeetService.googlemeetrequest(googleMeetRequestDto);
            log.info(googleMeetingLink.toString());
            String googlemeetlink=googleMeetingLink.getGoogleMeetingLink();
            approveResponse.setGoogleMeetingLink(googlemeetlink);
             */
            approveResponse.setGoogleMeetingLink("구글미팅 링크 생성로직은 아직 구현 전..");
            approveResponse.setPaymentId(savedpayment.getPaymentId());
            approveResponse.setPaymentstatus(savedpayment.getPaymentStatus());
            return approveResponse;
        } catch (RestClientException | URISyntaxException e) {
            log.error("결제 승인 실패", e);
            throw new BaseException(PaymentErrorCode.PAYMENT_PROCESSING_ERROR);
        }
    }
    public KakaoPayCancelDto kakaoPayCancel(KakaoPayCancelRequestDto requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaoAdminKey);
        headers.add("Content-type", "application/json");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", requestDTO.getTid());
        parameters.put("partner_order_id", requestDTO.getPartnerOrderId());
        parameters.put("partner_user_id", requestDTO.getPartnerUserId());
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

            KakaoPayCancelDto kakaoPayCancelDto = new KakaoPayCancelDto();
            kakaoPayCancelDto.setCid(cancelResponse.getCid());
            kakaoPayCancelDto.setTid(cancelResponse.getTid());
            kakaoPayCancelDto.setPaymentstatus(payment.getPaymentStatus());

            // 추후 디자이너 예약 확정 엔티티에서 해당 partner_order_id를 통해 예약 데이터를 삭제로직 구현
            // designerBookingRepository.deleteByPartnerOrderId(requestDTO.getPartnerOrderId());
            return kakaoPayCancelDto;
        } catch (RestClientException | URISyntaxException e) {
            log.error("결제 취소 실패", e);
            throw new BaseException(PaymentErrorCode.PAYMENT_CANCELLATION_ERROR);
        }
    }
}
