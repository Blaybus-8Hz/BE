package com.haertz.be.designer.controller;

import com.haertz.be.common.response.SuccessResponse;
import com.haertz.be.designer.dto.response.DesignerResponse;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.District;
import com.haertz.be.designer.entity.MeetingMode;
import com.haertz.be.designer.entity.Specialty;
import com.haertz.be.designer.service.DesignerService;
import com.haertz.be.designer.usecase.GetAllDesignersUseCase;
import com.haertz.be.designer.usecase.GetFilteredDesignersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/designer")
@RequiredArgsConstructor
@Tag(name = "디자이너 조회 api", description = "디자이너 조회 관련 API 입니다.")
public class DesignerController {

    private final DesignerService designerService;
    private final GetAllDesignersUseCase getAllDesignersUseCase;
    private final GetFilteredDesignersUseCase getFilteredDesignersUseCase;

    @Operation(summary = "디자이너 상세정보를 조회하는 api입니다.")
    @GetMapping("/{designerId}")
    public SuccessResponse<DesignerResponse> getDesigners(@PathVariable("designerId") Long designerId){
        DesignerResponse response = designerService.getDesignerResponse(designerId);
        return SuccessResponse.of(response);
    }

    @Operation(summary = "모든 디자이너를 조회하는 API입니다. 기본적으로 5개씩 페이징됩니다.")
    @GetMapping
    public SuccessResponse<Page<Designer>> getAllDesigners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "designerName") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Designer> designers =getAllDesignersUseCase.execute(pageable);
        return SuccessResponse.of(designers);
    }

    @Operation(summary = "필터링된 디자이너 조회", description = """
    지역, 대면/비대면 여부, 카테고리에 따라 디자이너를 필터링하여 조회합니다. 기본적으로 5개씩 페이징됩니다.
    [필터 조건]
    - meetingMode(대면/비대면 여부): REMOTE(비대면), FACE_TO_FACE(대면)
    - district(지역): SEOUL_ALL, GANGNAM_CHUNGDAM_APGUJUNG, HONGDAE_YEONNAM_HAPJEONG, SEONGSU_GUNDAE
    - specialty(전문 분야): DYEING, BLEACH, PERM
    """)
    @GetMapping("/filter")
    public SuccessResponse<Page<Designer>> getFilteredDesigners(
            @Parameter(name = "meetingMode", description = "대면/비대면 여부 * BOTH는 선택하지 마세요", example = "REMOTE")
            @RequestParam(required = false) MeetingMode meetingMode,

            @Parameter(name = "district", description = "지역 필터", example = "SEOUL_ALL")
            @RequestParam(required = false) District district,

            @Parameter(name = "specialty", description = "전문 분야 (염색, 탈색, 펌 등)", example = "DYEING")
            @RequestParam(required = false) Specialty specialty,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "designerName") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Designer> designers = getFilteredDesignersUseCase.execute(meetingMode, district, specialty, pageable);

        return SuccessResponse.of(designers);
    }



}