package com.dwj.vblogold.shiro;

import com.alibaba.fastjson.JSON;
import com.dwj.vblogold.controller.UserController;
import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Slf4j
public class MyRequestFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        String url = ((ShiroHttpServletRequest) servletRequest).getRequestURI();
        if (url.contains("swagger") || url.contains("api-docs") ||
                url.contains("login") || url.contains("logout") || url.contains("captcha") || url.contains(".ico") ||
                url.contains("queryArticles") || url.contains("queryArticleByIdAuthor") ||
                url.contains("getTags") || url.contains("getTimeline") || url.contains("getCurrentUser") ||
                url.contains("queryArticleListByTimeLine") || url.contains("queryArticleInfoById")||
                url.contains("uploadImage") || url.contains("getMessageBoard") ||
                url.contains("saveMessageBoard") || url.contains("queryArticleListByVisits") ||
                url.contains("signUp")) {

            return true;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpSession httpSession = ((ShiroHttpServletRequest) servletRequest).getSession();

        String loginName = (String) httpSession.getAttribute(UserController.LOGIN_NAME_KEY);
        if (!StringUtils.hasLength(loginName)) {
            JsonResponse jsonResponse = JsonResponse.response(ResponseCode.NOT_LOGIN);
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(jsonResponse));

            log.info("Not Login");
            return false;
        }

        return true;
    }
}
