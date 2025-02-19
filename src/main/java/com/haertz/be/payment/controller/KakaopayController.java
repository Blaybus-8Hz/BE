package com.haertz.be.payment.controller;

import com.haertz.be.common.response.SuccessResponse;
import com.haertz.be.payment.service.KakaoPayService;
import com.haertz.be.payment.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "카카오 페이 API", description = "카카오페이 결제 연동을 위한 API입니다.")
@RestController
@Log
public class KakaopayController {
    @Autowired
    private KakaoPayService kakaoPayService;

    @Operation(summary = "카카오페이 결제 요청을 보냅니다.")
    @PostMapping("/api/kakaoPay")
    public ResponseEntity<KakaoPayDTO> kakaoPayGet(@RequestBody KakaoPayRequestDTO requestDTO){
        log.info("결제하기 버튼 클릭 시 카카오페이 결제 요청");

        KakaoPayDTO kakaoPayDTO=kakaoPayService.kakaoPayReady(requestDTO);
        if(kakaoPayDTO!=null){
            return ResponseEntity.ok(kakaoPayDTO);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @Operation(summary = "카카오페이 결제 승인요청을 보냅니다.")
    @PostMapping("/api/kakaoPay/approve")
    public ResponseEntity<KakaoPayApproveDto> kakaoPayApprove(@RequestBody KakaoPayApproveRequestDto requestDTO){
        //log.info("카카오페이 결제 승인요청:{}",requestDTO.getTid());
        KakaoPayApproveDto approveResponse = kakaoPayService.kakaoPayApprove(requestDTO);
        if (approveResponse != null) {
            return ResponseEntity.ok(approveResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /*
    @Operation(summary="카카오페이 결제 취소 요청을 보냅니다. 결제가 완료된(승인된) 건에 대해 환불 처리를 하기 위해 Kakao Pay의 결제 취소 API를 호출하는 로직입니다.")
    @PostMapping("/api/kakaPay/refund")
    public ResponseEntity<KakaoPayCancelDto> kakaoPaycancel(@RequestBody KakaoPayCancelRequestDto requestDTO){
        KakaoPayCancelDto approveResponse=kakaoPayService.kakaoPayCancel(requestDTO);
        if (approveResponse != null) {
            return ResponseEntity.ok(approveResponse);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

     */
    @Operation(summary="카카오페이 결제중(결제 완료하기 전임) 취소를 선택할경우, 결제 완료 후 취소는 예약삭제 api이용.")
    @PostMapping("/api/kakaoPay/cancel")
    public SuccessResponse payingcancel(@RequestBody PayingCancelRequestDto requestDTO){
        kakaoPayService.payingcancel(requestDTO);
        return SuccessResponse.empty();
    }
}
