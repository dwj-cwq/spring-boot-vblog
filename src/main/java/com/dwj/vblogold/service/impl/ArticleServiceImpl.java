package com.dwj.vblogold.service.impl;

import com.dwj.vblogold.entity.ArticleEntity;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.TimelineEntity;
import com.dwj.vblogold.repository.ArticleRepository;
import com.dwj.vblogold.repository.TimelineRepository;
import com.dwj.vblogold.service.ArticleService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public PageList<ArticleEntity> queryArticleList(String key, Integer offset, Integer limit) {
        PageList<ArticleEntity> articlePageList = new PageList<>();
        List<ArticleEntity> articleEntityList = articleRepository.queryArticleListByKey(key, offset - 1, limit);
        int total = articleRepository.queryArticleTotalByKey(key);

        articlePageList.setRows(articleEntityList);
        articlePageList.setTotal(total);
        return articlePageList;

    }

    @Override
    public PageList<ArticleEntity> queryArticleListByTimeLine(String TimeLine, Integer offset, Integer limit) {
        PageList<ArticleEntity> articlePageList = new PageList<>();
        List<ArticleEntity> articleEntityList = articleRepository.queryArticleListByTimeLine(TimeLine, offset - 1, limit);
        int total = articleRepository.queryArticleTotalByTimeLine(TimeLine);

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

        ArticleEntity preArticleEntity = articleRepository.queryPrePageId();

        if (preArticleEntity != null) {
            long nextArticleId = preArticleEntity.getId() + 1;
            // 更新上一页的下页id
            articleRepository.updatePrePageNextId(nextArticleId);
            articleEntity.setLastArticleId(preArticleEntity.getId());
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
        return articleRepository.findOne((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (articleId != null) {
                predicates.add(cb.equal(root.get("artilce_id"), articleId));
            }
            if (!Strings.isNullOrEmpty(author)) {
                predicates.add(cb.equal(root.get("author"), author));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }).get();
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
}
