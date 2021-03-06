package com.dwj.vblogold.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Component
public class ErrorConfig  implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry){
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/index.html");
        registry.addErrorPages(error400Page);
    }
}
