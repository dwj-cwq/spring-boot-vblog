package com.dwj.vblogold.repository;

import com.dwj.vblogold.entity.LogEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author dwj
 * @date 2020-06-17 15:52
 */
public interface LogRepository extends JpaRepositoryImplementation<LogEntity, Long> {
    @Query(value = "select * from log l where l.ip like concat('%',:keyname,'%') or l.operation like concat('%',:keyname,'%' ) " +
            "order by l.create_time desc limit :offset, :limit", nativeQuery = true)
    List<LogEntity> queryLogList(@Param("key") String keyName, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "select count(*) from log l where l.ip like concat('%',:keyName,'%') or l.operation like concat('%',:keyName,'%')", nativeQuery = true)
    int queryLogTotal(@Param("key") String keyName);
}
