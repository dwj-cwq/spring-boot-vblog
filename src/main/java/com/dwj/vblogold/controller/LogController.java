package com.dwj.vblogold.controller;

import com.dwj.vblogold.entity.LogEntity;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@RestController
public class LogController {
    @Autowired
    private LogService logService;
    @ResponseBody
    @GetMapping("/getLog")
    public JsonResponse getLog(String key, int offset, int limit) {
        PageList<LogEntity> PageLogList = logService.queryList(key, offset, limit);
        return JsonResponse.success(PageLogList);
    }
}
