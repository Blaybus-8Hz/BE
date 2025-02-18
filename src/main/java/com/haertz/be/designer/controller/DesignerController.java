package com.haertz.be.designer.controller;

import com.haertz.be.common.response.SuccessResponse;
import com.haertz.be.designer.dto.response.DesignerResponse;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.Specialty;
import com.haertz.be.designer.service.DesignerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/designer")
@RequiredArgsConstructor
@Tag(name = "디자이너 조회 api", description = "디자이너 조회 관련 API 입니다.")
public class DesignerController {

    private final DesignerService designerService;

    @Operation(summary = "디자이너 상세정보를 조회하는 api입니다.")
    @GetMapping("/{designerId}")
    public SuccessResponse<DesignerResponse> getDesigners(@PathVariable("designerId") Long designerId){
        DesignerResponse response = designerService.getDesignerResponse(designerId);
        return SuccessResponse.of(response);
    }

    @Operation(
            summary = "카테고리별로 디자이너 리스트를 조회하는 API입니다.",
            parameters = {
                    @Parameter(name = "category", description = "디자이너 리스트를 조회할 카테고리 (펌 : PERM, 염색 : DYEING, 탈염색 : BLEACH)", required = true)
            }
    )
    @GetMapping("/list/{category}")
    public SuccessResponse<List<Designer>> getDesignerList(@PathVariable("category") Specialty category) {
        List<Designer> list = designerService.getDesignerResponseByCategories(category);
        return SuccessResponse.of(list);
    }


    @GetMapping("/list/all")
    public SuccessResponse<List<Designer>> getAllList(){
        List<Designer> list = designerService.getAll();
        return SuccessResponse.of(list);
    }


}
