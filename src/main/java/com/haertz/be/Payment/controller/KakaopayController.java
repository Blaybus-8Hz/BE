package com.haertz.be.payment.controller;

import com.haertz.be.payment.dto.KakaoPayApproveDTO;
import com.haertz.be.payment.dto.KakaoPayApproveRequestDTO;
import com.haertz.be.payment.dto.KakaoPayDTO;
import com.haertz.be.payment.dto.KakaoPayRequestDTO;
import com.haertz.be.payment.Service.KakaoPayService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Tag(name="카카오페이 api",description="카카오페이api 결제 연동을 위한 API")
@RestController
@Log
public class KakaopayController {
    @Autowired
    private KakaoPayService kakaoPayService;

    @PostMapping("/api/kakaoPay") //?post?get?
    public ResponseEntity<KakaoPayDTO> kakaoPayGet(@RequestBody KakaoPayRequestDTO requestDTO){
        log.info("결제하기 버튼 클릭 시 카카오페이 결제 요청");

        /*프론트에서 유저 아이디 넘겨주는게 아닌, 토큰에서 유저정보 가져오는 코드로 변경 필요.(ex.JWT토큰 디코딩)
        코드...
         */

        KakaoPayDTO kakaoPayDTO=kakaoPayService.kakaoPayReady(requestDTO);
        if(kakaoPayDTO!=null){
            return ResponseEntity.ok(kakaoPayDTO);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PostMapping("/api/kakaoPay/approve")
    public ResponseEntity<KakaoPayApproveDTO> kakaoPayApprove(@RequestBody KakaoPayApproveRequestDTO requestDTO){
        //log.info("카카오페이 결제 승인요청:{}",requestDTO.getTid());
        KakaoPayApproveDTO approveResponse = kakaoPayService.kakaoPayApprove(requestDTO);
        if (approveResponse != null) {
            return ResponseEntity.ok(approveResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
