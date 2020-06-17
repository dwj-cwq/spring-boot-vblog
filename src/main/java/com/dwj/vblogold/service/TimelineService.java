package com.dwj.vblogold.service;

import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.TimelineEntity;

import java.util.List;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public interface TimelineService {

    /**
     * 查询所有的时间轴
     * @param offset 偏移量
     * @param limit 条数
     * @return 文章列表
     */
    PageList<TimelineEntity> queryTimelineList(Integer offset, Integer limit);

    /**
     * 保存新的时间轴
     * @param timelineEntity 时间轴对象
     */
    void saveTimeline(TimelineEntity timelineEntity);

    /**
     * 通过name查询所有时间轴
     * @param name 名称
     */
    List<TimelineEntity> queryTimelineListByName(String name);
}
