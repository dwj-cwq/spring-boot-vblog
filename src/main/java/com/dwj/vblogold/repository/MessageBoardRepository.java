package com.dwj.vblogold.repository;

import com.dwj.vblogold.entity.MessageBoardEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author dwj
 * @date 2020-06-17 15:53
 */
public interface MessageBoardRepository extends JpaRepositoryImplementation<MessageBoardEntity, Long> {
    @Modifying
    @Query(value = "select * from message_board order by message_board.create_time desc limit :offset - 1, :limit", nativeQuery = true)
    List<MessageBoardEntity> queryMessageList(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
