package com.dwj.vblogold.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author dwj
 * @date 2020-06-09 22:29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class JsonResponse {
    int code;

    String msg;

    Object data;

    @JsonIgnore
    Map<String, Object> complexProps;

    private JsonResponse(){
        code = ResponseCode.OK.getCode();
        msg = ResponseCode.OK.getMessage();
    }

    private JsonResponse(Object value){
        code = ResponseCode.OK.getCode();
        msg = ResponseCode.OK.getMessage();
        data = value;
    }

    private JsonResponse(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getMessage();
    }

    public static JsonResponse response(ResponseCode responseCode){
        return new JsonResponse(responseCode);
    }

    public static JsonResponse success(){
        return new JsonResponse();
    }

    public static JsonResponse success(Object data){
        return new JsonResponse(data);
    }
}
