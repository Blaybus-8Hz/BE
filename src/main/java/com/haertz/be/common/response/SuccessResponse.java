package com.haertz.be.common.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SuccessResponse<T> extends BaseResponse {

    private final T data;

    public SuccessResponse(T data, String code){
        super(true, code, "호출에 성공하셨습니다.");
        this.data = data;
    }

    public static <T> SuccessResponse<T> of(T data){
        return new SuccessResponse<>(data, "200");
    }

    public static <T> SuccessResponse<T> empty(){
        return new SuccessResponse<>(null, "200");
    }

    public static <T> SuccessResponse<T> created(){
        return new SuccessResponse<>(null, "201");
    }
}
