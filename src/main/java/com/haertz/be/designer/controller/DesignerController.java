package com.haertz.be.designer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/designer")
@RequiredArgsConstructor
@Tag(name = "디자이너 조회 api", description = "디자이너 조회 관련 API 입니다.")
public class DesignerController {

    @GetMapping
    @Operation(summary = "디자이너 정보를 조회합니다.")
    public void getDesigners(){

    }
}
