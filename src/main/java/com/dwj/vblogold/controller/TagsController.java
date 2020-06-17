package com.dwj.vblogold.controller;

import com.dwj.vblogold.aop.ControllerLog;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.TagsEntity;
import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@RestController
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @ResponseBody
    @GetMapping("/getTags")
    public JsonResponse getTags() {
        PageList<TagsEntity> PageLogList = tagsService.queryTagsList();
        return JsonResponse.success(PageLogList);
    }

    @ControllerLog("保存标签")
    @PostMapping("/saveTags")
    public JsonResponse saveTags(HttpServletRequest request, TagsEntity tagsEntity) throws IOException {
        request.setCharacterEncoding("utf-8");
        tagsService.saveTags(tagsEntity);
        return JsonResponse.success();
    }

}
