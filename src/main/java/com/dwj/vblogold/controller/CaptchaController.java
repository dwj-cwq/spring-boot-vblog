package com.dwj.vblogold.controller;

import com.dwj.vblogold.aop.ControllerLog;
import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.response.ResponseCode;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@RestController("CaptchaController")
@RequestMapping("/api/captcha")
@Slf4j
public class CaptchaController {

    @Autowired
    public Producer producer;

    @GetMapping("/get")
    @ControllerLog("获取验证码")
    public void get(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        log.info("captcha:{}", text);
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @PostMapping("/verify/{captcha}")
    @ResponseBody
    @ControllerLog
    public JsonResponse verify(@PathVariable("captcha") String captcha, HttpSession session) {
        String sessionCaptcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!StringUtils.hasLength(sessionCaptcha)) {
            return JsonResponse.response(ResponseCode.NOT_GET_CAPTCHA);
        }

        if (!sessionCaptcha.equalsIgnoreCase(captcha)) {
            return JsonResponse.response(ResponseCode.CAPTCHA_VERIFY_FAILED);
        }
        session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        session.setAttribute(Constants.KAPTCHA_SESSION_DATE, System.currentTimeMillis());
        return JsonResponse.success();
    }
}
