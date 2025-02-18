package com.haertz.be.googlemeet.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleMeetRequestDto {
    private Long userId;
    private Long reservationId;
    //로컬테스트용
    private String googlemeetaccessToken;
}
