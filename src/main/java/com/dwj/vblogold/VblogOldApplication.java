package com.dwj.vblogold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing
public class VblogOldApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(VblogOldApplication.class, args);
    }

}
