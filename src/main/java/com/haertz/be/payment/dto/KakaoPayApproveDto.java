package com.haertz.be.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayApproveDto {
    private String googleMeetingLink;
    //임시로 카카오페이api에서 반환하는 정보 모두 response에 포함.
    private String aid; //결제 고유번호
    private String tid; //결제 거래번호
    private String cid; //가맹점 코드
    private String sid;
    private String partner_order_id; //기멩점 주문번호
    private String partner_user_id; //가맹점 회원 id
    private String payment_method_type; //결제 수단
    private Amount amount; //결제 금액 정보 (총금액, 면세금액, 부가세 등)
    private String item_name; //상품명
    private String item_code; //상품코드
    private int quantity; //상품 수량
    private String created_at; //결제 준비 시간
    private String approved_at; //결제 승인 시간
    private String payload; //추가데이터(필요시 사용)
}
