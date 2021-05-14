package com.dwj.vblogold.service;

import com.dwj.vblogold.entity.ArticleEntity;
import com.dwj.vblogold.entity.PageList;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public interface ArticleService {

    /**
     * 根据模糊字段查询
     *
     * @param key    字段
     * @param offset 偏移量
     * @param limit  条数
     * @return 文章列表
     */
    PageList<ArticleEntity> queryArticleList(String key, Integer offset, Integer limit);

    /**
     * 分页获取所有 article
     *
     * @param offset 偏移量
     * @param limit  条数
     * @return 文章列表
     */
    PageList<ArticleEntity> getAllArticles(Integer offset, Integer limit);

    /**
     * 根据时间轴字段查询
     *
     * @param timeLine 时间轴
     * @param offset   偏移量
     * @param limit    条数
     * @return 文章列表
     */
    PageList<ArticleEntity> queryArticleListByTimeLine(String timeLine, Integer offset, Integer limit);

    /**
     * 按访问量排序
     *
     * @param offset 偏移量
     * @param limit  条数
     * @return 文章列表
     */
    PageList<ArticleEntity> queryArticleListByVisits(Integer offset, Integer limit);


    /**
     * 保存文章
     *
     * @param articleEntity 文章对象
     */
    void saveArticle(ArticleEntity articleEntity);

    /**
     * 通过作者和id查询文章
     *
     * @param articleId 文章id
     * @param author    作者
     * @return 文章列表
     */
    ArticleEntity queryArticle(Long articleId, String author);

    /**
     * 通过作者查询文章
     *
     * @param articleId 文章id
     * @param author    作者
     */
    void deleteArticle(Long articleId, String author);

    /**
     * 更新文章的访问量
     */
    void updateArticleVisits(Long articleId, String author);

    /**
     * 通过id查询文章简要信息，不包含文章内容
     *
     * @param id id
     * @return 文章列表
     */
    ArticleEntity queryArticleInfoById(Long id);
}
