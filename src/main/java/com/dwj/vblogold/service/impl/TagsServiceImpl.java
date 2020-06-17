package com.dwj.vblogold.service.impl;

import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.TagsEntity;
import com.dwj.vblogold.repository.TagsRepository;
import com.dwj.vblogold.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public PageList<TagsEntity> queryTagsList() {
        PageList<TagsEntity> tagsPageList = new PageList<>();

        List<TagsEntity> tagsEntityList = tagsRepository.findAll();
        int total = (int) tagsRepository.count();

        tagsPageList.setRows(tagsEntityList);
        tagsPageList.setTotal(total);
        return tagsPageList;
    }

    @Override
    public void saveTags(TagsEntity tagsEntity) {
        tagsEntity.setCreateTime(new Date());
        tagsRepository.save(tagsEntity);
    }
}
