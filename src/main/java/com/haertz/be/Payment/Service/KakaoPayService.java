package com.haertz.be.Payment.Service;

import com.haertz.be.Payment.DTO.KakaoPayApproveDTO;
import com.haertz.be.Payment.DTO.KakaoPayApproveRequestDTO;
import com.haertz.be.Payment.DTO.KakaoPayDTO;
import com.haertz.be.Payment.DTO.KakaoPayRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class KakaoPayService {
    private static final String Host = "https://open-api.kakaopay.com/online";
    @Value("${kakao.pay.kakaoAdminKey}")
    private String kakaoAdminKey;
    //프론트가 배포 한 주소로 설정하기, 사용자가 결제완료 버튼을 누르면 ex.approvalurl(성공시)로 리디렉트되고 이때 pg_token이 포함되어있음,이 토큰 받아 api/kakapay/approve로 요청보내면 됨.
    @Value("${kakao.pay.approveUrl}")
    private String approveUrl;
    @Value("${kakao.pay.cancelUrl}")
    private String cancelUrl;
    @Value("${kakao.pay.failUrl}")
    private String failUrl;

    private KakaoPayDTO kakaoPayDTO;
    private String cid = "TC0ONETIME"; //가맹점용 코드(테스트용)
    private final RestTemplate restTemplate= new RestTemplate();

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
    public KakaoPayApproveDTO kakaoPayApprove(KakaoPayApproveRequestDTO requestDTO) {
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
            KakaoPayApproveDTO approveResponse = restTemplate.postForObject(
                    new URI(Host + "/v1/payment/approve"), body, KakaoPayApproveDTO.class);
            log.info("결제 승인 응답: {}", approveResponse);
            return approveResponse;
        } catch (RestClientException | URISyntaxException e) {
            log.error("결제 승인 실패", e);
            return null;
        }
    }
}
