package com.dwj.vblogold.repository;

import com.dwj.vblogold.dto.ArticleInfo;
import com.dwj.vblogold.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author dwj
 * @date 2020-06-17 15:52
 */
public interface ArticleRepository extends JpaRepositoryImplementation<ArticleEntity, Long> {

    @Query(value = "select * from article a where a.author like concat('%',:keyName,'%') " +
            "or a.article_title like concat('%',:keyName,'%') " +
            "or a.categories like concat('%',:keyName,'%') " +
            "order by a.create_time desc limit :offset, :limit", nativeQuery = true)
    List<ArticleEntity> queryArticleListByKey(@Param("keyName") String keyName, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select * from article a order by a.create_time desc limit ?1, ?2", nativeQuery = true)
    List<ArticleEntity> findAllArticles(int offset, int limit);

    @Query(value = "select count(*) from article where article.author like concat('%',:keyName,'%') " +
            "or article.article_title like concat('%',:keyName,'%') " +
            "or article.categories like concat('%',:keyName,'%')", nativeQuery = true)
    int queryArticleTotalByKey(@Param("keyName") String keyName);

    @Query(value = "select * from article a where a.timeline =:timeLine order by a.visits desc limit :offset, :limit", nativeQuery = true)
    List<ArticleEntity> queryArticleListByTimeLine(@Param("timeLine") String timeLine, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select count(*) from article a where a.timeline =:timeLine", nativeQuery = true)
    int queryArticleTotalByTimeLine(@Param("timeLine") String timeLine);

    @Query(value = "select * from article a order by a.visits desc limit :offset ,:limit", nativeQuery = true)
    List<ArticleEntity> queryArticleListByVisits(@Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select id from article a order by a.create_time desc limit 1", nativeQuery = true)
    ArticleInfo queryPrePageId();

    @Modifying
    @Query(value = "update article set next_article_id =:nextArticleId order by create_time desc limit 1", nativeQuery = true)
    void updatePrePageNextId(@Param("nextArticleId") long nextArticleId);

    @Modifying
    @Query(value = "update article set article.visits = article.visits + 1 where article.article_id =:articleId and article.author =:author", nativeQuery = true)
    void updateArticleVisits(@Param("articleId") long articleId, @Param("author") String author);

    @Query(value = "select * from article a where a.article_id = ?1 AND a.author = ?2", nativeQuery = true)
    ArticleEntity queryArticle(Long articleId, String author);

}
