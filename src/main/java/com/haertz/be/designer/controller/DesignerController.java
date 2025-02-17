package com.haertz.be.designer.controller;

import com.haertz.be.common.response.SuccessResponse;
import com.haertz.be.designer.dto.DesignerResponse;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.service.DesignerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name="디자이너 조회", description = "디자이너 조회를 위한 api입니다.")
@Controller
@RequiredArgsConstructor
public class DesignerController {

    private final DesignerService designerService;


    @Operation(summary = "디자이너 정보를 조회하는 api입니다.")
    @GetMapping("/api/designer/{designerId}")
    public SuccessResponse<DesignerResponse> getDesigners(@PathVariable("designerId") Long designerId){
        DesignerResponse response = designerService.getDesignerResponse(designerId);
        return SuccessResponse.of(response);
    }
}
