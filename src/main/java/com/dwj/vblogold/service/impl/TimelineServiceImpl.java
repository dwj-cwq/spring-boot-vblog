package com.dwj.vblogold.service.impl;

import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.TimelineEntity;
import com.dwj.vblogold.repository.TimelineRepository;
import com.dwj.vblogold.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Service
public class TimelineServiceImpl implements TimelineService {
    @Autowired
    private TimelineRepository timelineRepository;

    @Override
    public PageList<TimelineEntity> queryTimelineList(Integer offset, Integer limit) {
        PageList<TimelineEntity> timelinePageList = new PageList<>();

        List<TimelineEntity> timelineEntityList = timelineRepository.queryTimelineList(offset - 1, limit);
        int total = timelineRepository.findAll().size();

        timelinePageList.setRows(timelineEntityList);
        timelinePageList.setTotal(total);
        return timelinePageList;
    }

    @Override
    public void saveTimeline(TimelineEntity timelineEntity) {
        timelineRepository.save(timelineEntity);
    }

    @Override
    public List<TimelineEntity> queryTimelineListByName(String name) {
        return timelineRepository.queryTimelineListByName(name);
    }
}
