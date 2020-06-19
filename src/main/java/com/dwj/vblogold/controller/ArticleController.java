package com.dwj.vblogold.controller;

import com.dwj.vblogold.aop.ControllerLog;
import com.dwj.vblogold.entity.ArticleEntity;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.response.ImageResponse;
import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.service.ArticleService;
import com.dwj.vblogold.util.FileUtil;
import com.dwj.vblogold.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@RestController("ArticleController")
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @ControllerLog("查询文章")
    @GetMapping("/queryArticles")
    public JsonResponse getArticle(String key, int offset, int limit) {
        if (offset <= 0) {
            offset = 1;
        }
        if (limit <= 0) {
            limit = 10;
        }
        PageList<ArticleEntity> PageArticleList = articleService.queryArticleList(key, offset, limit);
        return JsonResponse.success(PageArticleList);
    }

    @ResponseBody
    @ControllerLog("查询文章")
    @GetMapping("/queryArticleListByTimeLine")
    public JsonResponse queryArticleListByTimeLine(String timeLine, int offset, int limit) {
        if (offset <= 0) {
            offset = 1;
        }
        if (limit <= 0) {
            limit = 10;
        }
        PageList<ArticleEntity> PageArticleList = articleService.queryArticleListByTimeLine(timeLine, offset, limit);
        return JsonResponse.success(PageArticleList);
    }

    @ResponseBody
    @ControllerLog("查询热点")
    @GetMapping("/queryArticleListByVisits")
    public JsonResponse queryArticleListByVisits(int offset, int limit) {
        if (offset <= 0) {
            offset = 1;
        }
        if (limit <= 0) {
            limit = 10;
        }
        PageList<ArticleEntity> PageArticleList = articleService.queryArticleListByVisits(offset, limit);
        return JsonResponse.success(PageArticleList);
    }


    @ControllerLog("发布文章")
    @PostMapping("/publishArticle")
    public JsonResponse publishArticle(HttpServletRequest request, ArticleEntity articleEntity) throws IOException {

        request.setCharacterEncoding("utf-8");

        articleService.saveArticle(articleEntity);

        return JsonResponse.success();
    }

    @ControllerLog("查询文章内容")
    @GetMapping("/queryArticleByIdAuthor")
    public JsonResponse queryArticleByIdAuthor(Long articleId, String author) {
        ArticleEntity articleEntity = articleService.queryArticle(articleId, author);

        articleService.updateArticleVisits(articleId, author);
        return JsonResponse.success(articleEntity);
    }

    @GetMapping("/queryArticleInfoById")
    public JsonResponse queryArticleInfoById(Long id) {
        ArticleEntity articleEntity = articleService.queryArticleInfoById(id);
        return JsonResponse.success(articleEntity);
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public ImageResponse uploadImage(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) {
        ImageResponse imageResponse = new ImageResponse();
        try {
            request.setCharacterEncoding("utf-8");
            //设置返回头后页面才能获取返回url
            response.setHeader("Content-Type", "text/html");

            FileUtil fileUtil = new FileUtil();
            String filePath = this.getClass().getResource("/").getPath().substring(1) + "blogImg/";
            String fileContentType = file.getContentType();
            String fileExtension = fileContentType.substring(fileContentType.indexOf("/") + 1);
            TimeUtil timeUtil = new TimeUtil();
            String fileName = timeUtil.getLongTime() + "." + fileExtension;

            String subCatalog = "blogArticles/" + new TimeUtil().getFormatDateForThree();
            String fileUrl = fileUtil.uploadFile(fileUtil.multipartFileToFile(file, filePath, fileName), subCatalog);

            imageResponse.setSuccess(1);
            imageResponse.setMessage("上传成功");
            imageResponse.setUrl(fileUrl);
        } catch (Exception e) {
            try {
                response.getWriter().write("{\"success\":0}");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return imageResponse;
    }
}
