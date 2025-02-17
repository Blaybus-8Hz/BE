package com.haertz.be.googlemeet.controller;

import com.haertz.be.googlemeet.dto.GoogleMeetDto;
import com.haertz.be.googlemeet.dto.GoogleMeetRequestDto;
import com.haertz.be.googlemeet.service.GoogleMeetService;
import com.haertz.be.payment.Service.BankTransferService;
import com.haertz.be.payment.dto.BankTransferDto;
import com.haertz.be.payment.dto.BankTransferRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "구글 미팅 링크 생성 API", description = "구글미팅 링크생성을 위한 테스트용 API입니다.")
@RestController
@RequiredArgsConstructor
@Log
public class GoogleMeetController {
    private final GoogleMeetService googleMeetService;

    @Operation(summary = "해당 api를 통해 구글미팅 링크가 잘 생성되는지 테스트 할 수 있습니다.")
    @PostMapping("/api/test/googlemeet")
    public ResponseEntity<GoogleMeetDto> GoogleMeet(@RequestBody GoogleMeetRequestDto requestDTO) {
        GoogleMeetDto googleMeetDto = googleMeetService.googlemeetrequest(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(googleMeetDto);
    }
}
