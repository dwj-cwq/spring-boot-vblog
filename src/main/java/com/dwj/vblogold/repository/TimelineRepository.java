package com.dwj.vblogold.repository;

import com.dwj.vblogold.entity.TimelineEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author dwj
 * @date 2020-06-17 15:54
 */
public interface TimelineRepository extends JpaRepositoryImplementation<TimelineEntity, Long> {
    @Modifying
    @Query(value = "select * from timeline tl where tl.timeline =:name", nativeQuery = true)
    List<TimelineEntity> queryTimelineListByName(@Param("name") String name);

    @Modifying
    @Query(value = "select * from timeline tl order by tl.create_time desc limit :offset, :limit", nativeQuery = true)
    List<TimelineEntity> queryTimelineList(@Param("offset") Integer offset, @Param("limit") Integer limit);

}
