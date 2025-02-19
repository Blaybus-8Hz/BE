package com.haertz.be.designer.controller;

import com.haertz.be.common.response.SuccessResponse;
import com.haertz.be.designer.dto.response.DesignerResponse;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.District;
import com.haertz.be.designer.entity.MeetingMode;
import com.haertz.be.designer.entity.Specialty;
import com.haertz.be.designer.service.DesignerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            summary = "지역, 대면/비대면, 카테고리 조건으로 디자이너 리스트를 조회하는 API입니다.",
            parameters = {
                    @Parameter(name = "districts", description = "디자이너 리스트를 조회할 지역 목록 (SEOUL_ALL, GANGNAM_CHUNGDAM_APGUJUNG, HONGDAE_YEONNAM_HAPJEONG, SEONGSU_GUNDAE)", required = false),
                    @Parameter(name = "meetingModes", description = "디자이너 리스트를 조회할 대면/비대면 조건 (FACE_TO_FACE, BOTH, REMOTE)", required = false),
                    @Parameter(name = "categories", description = "디자이너 리스트를 조회할 카테고리 (예: SEOUL_ALL, GANGNAM_CHUNGDAM_APGUJUNG)", required = false)
            }
    )
    @GetMapping("/list/filter")
    public SuccessResponse<List<Designer>> getFilteredDesignerList(
            @RequestParam(value = "districts", required = false) List<District> districts,
            @RequestParam(value = "meetingModes", required = false) List<MeetingMode> meetingModes,
            @RequestParam(value = "categories", required = false) List<Specialty> categories,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        List<Designer> resultList = new ArrayList<>();

        if (districts != null && !districts.isEmpty()) {
            for (District district : districts) {
                List<Designer> designersByDistrict = designerService.getListByDistrict(district);
                resultList.addAll(designersByDistrict);
            }
        }

        if (meetingModes != null && !meetingModes.isEmpty()) {
            for (MeetingMode meetingMode : meetingModes) {
                List<Designer> designersByMeetingMode = designerService.getListByMeetingMode(meetingMode);
                resultList.addAll(designersByMeetingMode);
            }
        }

        if (categories != null && !categories.isEmpty()) {
            for (Specialty category : categories) {
                List<Designer> designersByCategory = designerService.getDesignerResponseByCategories(category);
                resultList.addAll(designersByCategory);
            }
        }
        
        List<Designer> pagedResultList = resultList.stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());

        return SuccessResponse.of(pagedResultList);
    }

//    @Operation(summary = "디자이너의 이미지 URL을 조회하는 API입니다.")
//    @GetMapping("/{designerId}/image")
//    public SuccessResponse<String> getDesignerImageUrl(@PathVariable Long designerId) {
//        Designer designer = designerService.getDesignerResponse(designerId);
//        String imageUrl = designer.getImageUrl();  // S3 URL을 반환
//        return SuccessResponse.of(imageUrl);
//    }


}
