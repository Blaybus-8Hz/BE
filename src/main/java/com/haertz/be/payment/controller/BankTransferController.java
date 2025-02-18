package com.haertz.be.payment.controller;

import com.haertz.be.payment.service.BankTransferService;
import com.haertz.be.payment.dto.BankTransferCancelDto;
import com.haertz.be.payment.dto.BankTransferCancelRequestDto;
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

@Tag(name = "계좌이체 API", description = "계좌이체를 위한 API입니다.")
@RestController
@RequiredArgsConstructor
@Log
public class BankTransferController {
    private final BankTransferService bankTransferService;
    @Operation(summary = "계좌이체 요청을 보냅니다.")
    @PostMapping("/api/banktransfer")
    public ResponseEntity<BankTransferDto> bankTransfer(@RequestBody BankTransferRequestDto requestDTO) {
        BankTransferDto bankTransferDto = bankTransferService.banktransferrequest(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankTransferDto);
    }
    /*
    @Operation(summary = "계좌이체 결제 취소 요청을 보냅니다.")
    @PostMapping("/api/banktransfer/cancel")
    public ResponseEntity<BankTransferCancelDto> cancelBankTransfer(@RequestBody BankTransferCancelRequestDto requestDTO) {
        BankTransferCancelDto bankTransferCancelDto = bankTransferService.banktransfercancel(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankTransferCancelDto);
    }

     */
}
