package com.haertz.be.designer.controller;

import com.haertz.be.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;

@Tag(name="디자이너 조회", description = "디자이너 조회를 위한 api입니다.")
@Controller
public class DesignerController {

    @Operation(summary = "디자이너 정보를 조회하는 api입니다.")
    public SuccessResponse<Object> getDesigners(){

        return SuccessResponse.of(null);
    }
}
