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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/list")
    public SuccessResponse<List<Designer>> getDesignerList(@RequestParam("categories") List<Specialty> categories) {
        List<Designer> resultList = new ArrayList<>();

        for (Specialty category : categories) {
            List<Designer> designers = designerService.getDesignerResponseByCategories(category);
            resultList.addAll(designers);
        }

        return SuccessResponse.of(resultList);
    }


    @Operation(summary = "디자이너 전체 칩눌렀을때 리스트를 조회하는 api입니다.. 일단 다갖다줍니다.")
    @GetMapping("/list/all")
    public SuccessResponse<List<Designer>> getAllList(){
        List<Designer> list = designerService.getAll();
        return SuccessResponse.of(list);
    }


    @Operation(
            summary = "지역별로 디자이너 리스트를 조회하는 API입니다.",
            parameters = {
                    @Parameter(name = "districts", description = "디자이너 리스트를 조회할 지역 목록 (SEOUL_ALL, GANGNAM_CHUNGDAM_APGUJUNG, HONGDAE_YEONNAM_HAPJEONG, SEONGSU_GUNDAE)", required = true)
            }
    )
    @GetMapping("/list/by-district")
    public SuccessResponse<List<Designer>> getDesignerListByDistricts(@RequestParam("districts") List<District> districts) {
        List<Designer> resultList = new ArrayList<>();

        for (District district : districts) {
            List<Designer> designers = designerService.getListByDistrict(district);
            resultList.addAll(designers);
        }

        return SuccessResponse.of(resultList);
    }

    @Operation(
            summary = "대면/비대면 별로 디자이너 리스트를 조회하는 API입니다.",
            parameters = {
                    @Parameter(name = "meetingModes", description = "디자이너 리스트를 조회할 카테고리 (FACE_TO_FACE, BOTH, REMOTE)", required = true)
            }
    )
    @GetMapping("/list/by-meetingmode")
    public SuccessResponse<List<Designer>> getDesignerListByMeetingModes(@RequestParam("meetingModes") List<MeetingMode> meetingModes) {
        List<Designer> resultList = new ArrayList<>();

        for (MeetingMode meetingMode : meetingModes) {
            List<Designer> designers = designerService.getListByMeetingMode(meetingMode);
            resultList.addAll(designers);
        }

        return SuccessResponse.of(resultList);
    }



}
