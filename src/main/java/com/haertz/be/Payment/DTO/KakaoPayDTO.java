package com.haertz.be.Payment.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

//카카오페이 api에서 넘겨준 정보 담는 dto
@Getter
@Setter
@ToString
public class KakaoPayDTO {
    private String tid;
    private String next_redirect_pc_url; //요청한 클라이언트가 pc 웹일 경우
    private String next_redirect_mobile_url; //요청한 클라이언트가 모바일웹일 경우
    private String next_redirect_app_url;
    private Date created_at;
}
