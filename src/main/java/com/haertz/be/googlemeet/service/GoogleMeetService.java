package com.haertz.be.googlemeet.service;

import com.haertz.be.auth.entity.User;
import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.auth.repository.UserRepository;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.googlemeet.dto.GoogleMeetDto;
import com.haertz.be.googlemeet.dto.GoogleMeetRequestDto;
import com.haertz.be.payment.dto.BankTransferRequestDto;
import com.haertz.be.payment.dto.PaymentSaveDto;
import com.haertz.be.payment.entity.PaymentMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log
public class GoogleMeetService {
    private final RestTemplate restTemplate=new RestTemplate();
    private final UserRepository userRepository; //유저 디비에서 구글 엑세스 토큰 조회

    public GoogleMeetDto googlemeetrequest(GoogleMeetRequestDto requestDTO){
        /*나중에 추가 구현
        //DB에서 해당 유저의 구글엑세스토큰 조회
        Long userId = requestDTO.getUserId();
        User user=userRepository.findAllById(userId)
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
        String googleaccessToken=user.getGoogleAccessToeken(user);
        if(googleaccessToken==null){
            throw new BaseException(UserErrorCode.GOOGLE_ACCESS_TOKEN_NOT_FOUND);
        }
         */
        String googleaccessToken=requestDTO.getGooglemeetaccessToken();
        String url = "https://www.googleapis.com/calendar/v3/calendars/primary/events?conferenceDataVersion=1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(googleaccessToken);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("summary", "Google Meet Test Meeting");
        requestBody.put("description", "Auto-generated meeting.");

        Map<String, Object> startTime = new HashMap<>();
        startTime.put("dateTime", "2025-02-18T10:00:00Z");
        startTime.put("timeZone", "UTC");

        Map<String, Object> endTime = new HashMap<>();
        endTime.put("dateTime", "2025-02-18T11:00:00Z");
        endTime.put("timeZone", "UTC");

        requestBody.put("start", startTime);
        requestBody.put("end", endTime);

        // conferenceData 설정
        Map<String, Object> conferenceData = new HashMap<>();
        Map<String, Object> createRequest = new HashMap<>();
        createRequest.put("requestId", UUID.randomUUID().toString()); // 고유한 requestId 생성
        createRequest.put("conferenceSolutionKey", Map.of("type", "hangoutsMeet"));
        conferenceData.put("createRequest", createRequest);
        requestBody.put("conferenceData", conferenceData);
        // conferenceDataVersion을 1로 설정하여 회의 세부 정보를 추가하도록 설정
        requestBody.put("conferenceDataVersion", 1);


        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        log.info("Google Meet Response: " + response);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Google Meet API 응답: " + response.getBody());
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("conferenceData")) {
                Map<String, Object> conferenceDataResponse = (Map<String, Object>) responseBody.get("conferenceData");
                if (conferenceDataResponse != null && conferenceDataResponse.containsKey("entryPoints")) {
                    List<Map<String, Object>> entryPointsList = (List<Map<String, Object>>) conferenceDataResponse.get("entryPoints");
                    if (entryPointsList != null && !entryPointsList.isEmpty()) {
                        Map<String, Object> entryPoint = entryPointsList.get(0);
                        String googleMeetLink = (String) entryPoint.get("uri");  // 여기에 미팅 링크가 있음
                        GoogleMeetDto googleMeetDto = new GoogleMeetDto();
                        googleMeetDto.setReservationId(requestDTO.getReservationId());
                        googleMeetDto.setGoogleMeetingLink(googleMeetLink);  // 미팅 링크 반환
                        return googleMeetDto;
                    }
                }
        }
    }throw new RuntimeException("Failed to create Google Meet link");
    }
}
