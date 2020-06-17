package com.dwj.vblogold.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "article", indexes = {@Index(columnList = "id", name = "id")})
public class ArticleEntity extends BaseEntity {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "author")
    private String author;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "type")
    private String type;

    @Column(name = "summary")
    private String summary;

    @Column(name = "categories")
    private String categories;

    @Column(name = "content")
    private String content;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "last_article_id")
    private Long lastArticleId;

    @Column(name = "next_article_id")
    private String nextArticleId;

    @Column(name = "timeline")
    private String timeline;

    @Column(name = "visits")
    private Integer visits;
}
