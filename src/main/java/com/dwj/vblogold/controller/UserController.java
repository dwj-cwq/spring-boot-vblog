package com.dwj.vblogold.controller;

import com.dwj.vblogold.aop.ControllerLog;
import com.dwj.vblogold.entity.UserEntity;
import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.response.ResponseCode;
import com.dwj.vblogold.service.UserService;
import com.google.code.kaptcha.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@RestController("UserController")
@RequestMapping("/api/user")
public class UserController {
    public final static String LOGIN_NAME_KEY = "LOGIN_NAME";

    @Autowired
    private UserService userService;

    @ControllerLog("创建用户")
    @PostMapping
    public JsonResponse createUser(@RequestBody UserEntity userEntity) {
        return JsonResponse.success(userService.addUser(userEntity));
    }

    @ControllerLog("更新用户")
    @PutMapping
    public JsonResponse updateUser(@RequestBody UserEntity userEntity) {
        return JsonResponse.success(userService.updateUser(userEntity));
    }

    @ControllerLog("删除用户")
    @DeleteMapping("/{userId}")
    public JsonResponse deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
        return JsonResponse.success();
    }

    @ResponseBody
    @ControllerLog("查询用户")
    @GetMapping("/queryUser")
    public JsonResponse getArticle(String username, int offset, int limit) {
        return JsonResponse.success(userService.queryUserList(username, offset, limit));
    }


    @ResponseBody
    @ControllerLog("查询用户")
    @GetMapping("/queryUserName")
    public JsonResponse queryUserName(String username) {
        return JsonResponse.success(userService.queryUserByName(username));
    }

    @ControllerLog("用户登录")
    @PostMapping("/login")
    public JsonResponse login(String username, String password, HttpSession session) {
        Long verifyTime = (Long) session.getAttribute(Constants.KAPTCHA_SESSION_DATE);
        session.removeAttribute(Constants.KAPTCHA_SESSION_DATE);
        if (null == verifyTime || System.currentTimeMillis() - verifyTime > 5 * 60 * 1000) {
            return JsonResponse.response(ResponseCode.CAPTCHA_VERIFY_FAILED);
        }

        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));

        session.setAttribute(LOGIN_NAME_KEY, username);

        return JsonResponse.success();
    }

    @ControllerLog("用户登出")
    @PostMapping("/logout")
    public JsonResponse logout(HttpSession session) {
        session.removeAttribute(LOGIN_NAME_KEY);

        return JsonResponse.success();
    }

    @GetMapping("/getCurrentUser")
    public JsonResponse getCurrentUser(HttpSession session) {
        String loginName = (String) session.getAttribute(LOGIN_NAME_KEY);
        return JsonResponse.success(userService.queryUserInfoByName(loginName));
    }
}
