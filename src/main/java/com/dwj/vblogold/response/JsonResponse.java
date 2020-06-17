package com.dwj.vblogold.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

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
        code = HttpStatus.OK.value();
        msg = HttpStatus.OK.getReasonPhrase();
    }

    private JsonResponse(Object value){
        code = HttpStatus.OK.value();
        msg = HttpStatus.OK.getReasonPhrase();
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
