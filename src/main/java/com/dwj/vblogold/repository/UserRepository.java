package com.dwj.vblogold.repository;

import com.dwj.vblogold.dto.CurrentUserInfo;
import com.dwj.vblogold.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dwj
 * @date 2020-06-09 22:49
 */
@Repository("UserRepository")
public interface UserRepository extends JpaRepositoryImplementation<UserEntity, Long> {
    @Query(value = "select * from user u where u.user_name like concat('%',:username,'%') order by u.create_time desc limit :offset, :limit", nativeQuery = true)
    List<UserEntity> queryUserList(@Param("username") String username, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "select count(*) from user u where u.username like concat('%',:username,'%')", nativeQuery = true)
    int queryUserTotal(@Param("username") String username);

    @Query(value = "select * from user u where u.user_name =:username", nativeQuery = true)
    UserEntity queryUserByName(@Param("username") String username);

    @Query(value = "select u.user_name as userName, u.avatar_url as avatarUrl from user u where u.user_name =:username", nativeQuery = true)
    CurrentUserInfo queryUserInfoByName(@Param("username") String username);
}
