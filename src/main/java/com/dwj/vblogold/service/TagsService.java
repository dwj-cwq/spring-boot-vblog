package com.dwj.vblogold.service;

import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.TagsEntity;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public interface TagsService {

    PageList<TagsEntity> queryTagsList();

    void saveTags(TagsEntity tagsEntity);
}
