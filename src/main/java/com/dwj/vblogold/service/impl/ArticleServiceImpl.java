package com.dwj.vblogold.service.impl;

import com.dwj.vblogold.dto.ArticleInfo;
import com.dwj.vblogold.entity.ArticleEntity;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.TimelineEntity;
import com.dwj.vblogold.repository.ArticleRepository;
import com.dwj.vblogold.repository.TimelineRepository;
import com.dwj.vblogold.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TimelineRepository timelineRepository;

    @Override
    public PageList<ArticleEntity> getAllArticles(Integer offset, Integer limit) {
        PageList<ArticleEntity> articlePageList = new PageList<>();
        List<ArticleEntity> articleEntityList = articleRepository.findAllArticles(offset - 1, limit);
        int total = (int) articleRepository.count();

        articlePageList.setRows(articleEntityList);
        articlePageList.setTotal(total);
        return articlePageList;
    }

    @Override
    public PageList<ArticleEntity> queryArticleList(String key, Integer offset, Integer limit) {
        PageList<ArticleEntity> articlePageList = new PageList<>();
        List<ArticleEntity> articleEntityList = articleRepository.queryArticleListByKey(key, offset - 1, limit);
        int total = articleRepository.queryArticleTotalByKey(key);

        articlePageList.setRows(articleEntityList);
        articlePageList.setTotal(total);
        return articlePageList;

    }

    @Override
    public PageList<ArticleEntity> queryArticleListByTimeLine(String timeLine, Integer offset, Integer limit) {
        PageList<ArticleEntity> articlePageList = new PageList<>();
        List<ArticleEntity> articleEntityList = articleRepository.queryArticleListByTimeLine(timeLine, offset - 1, limit);
        int total = articleRepository.queryArticleTotalByTimeLine(timeLine);

        articlePageList.setRows(articleEntityList);
        articlePageList.setTotal(total);
        return articlePageList;

    }

    @Override
    public PageList<ArticleEntity> queryArticleListByVisits(Integer offset, Integer limit) {
        PageList<ArticleEntity> articlePageList = new PageList<>();
        List<ArticleEntity> articleEntityList = articleRepository.queryArticleListByVisits(offset - 1, limit);
        int total = articleRepository.queryArticleTotalByKey("");
        articlePageList.setRows(articleEntityList);
        articlePageList.setTotal(total);
        return articlePageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveArticle(ArticleEntity articleEntity) {
        Date now = new Date();
        long articleId = System.currentTimeMillis();
        // 设置时间轴
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

        String timelineName = dateFormat.format(new Date());

        ArticleInfo preArticleInfo = articleRepository.queryPrePageId();

        if (preArticleInfo != null) {
            long nextArticleId = preArticleInfo.getId() + 1;
            // 更新上一页的下页id
            articleRepository.updatePrePageNextId(nextArticleId);
            articleEntity.setLastArticleId(preArticleInfo.getId());
        }

        articleEntity.setTimeline(timelineName);
        articleEntity.setCreateTime(now);
        articleEntity.setArticleId(articleId);
        articleEntity.setVisits(1);


        articleRepository.save(articleEntity);

        if (timelineRepository.queryTimelineListByName(timelineName).size() == 0) {
            TimelineEntity timelineEntity = new TimelineEntity();
            timelineEntity.setTimeline(timelineName);
            timelineEntity.setCreateTime(now);
            timelineRepository.save(timelineEntity);
        }
    }

    @Override
    public ArticleEntity queryArticle(Long articleId, String author) {
        return articleRepository.queryArticle(articleId, author);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleVisits(Long articleId, String author) {
        articleRepository.updateArticleVisits(articleId, author);
    }

    @Override
    public ArticleEntity queryArticleInfoById(Long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public void deleteArticle(Long articleId, String author) {
        articleRepository.deleteByArticleIdAndAuthor(articleId, author);
    }
}
