package com.dwj.vblogold.repository;

import com.dwj.vblogold.entity.TagsEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author dwj
 * @date 2020-06-17 15:54
 */
public interface TagsRepository extends JpaRepositoryImplementation<TagsEntity, Long> {
}
