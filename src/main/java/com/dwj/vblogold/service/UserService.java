package com.dwj.vblogold.service;

import com.dwj.vblogold.dto.CurrentUserInfo;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.UserEntity;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public interface UserService {

    UserEntity addUser(UserEntity userEntity);

    UserEntity updateUser(UserEntity userEntity);

    void deleteUserById(Long id);

    /**
     * 根据用户名模糊查询用户
     *
     * @param username 用户名
     * @param offset   偏移量
     * @param limit    条数
     * @return 用户
     */
    PageList<UserEntity> queryUserList(String username, Integer offset, Integer limit);

    /**
     * 保存用户
     *
     * @param user 用户对象
     */
    void saveUser(UserEntity user);

    /**
     * 根据用户名查询当前用户，包含密码
     *
     * @param username 用户名
     */
    UserEntity queryUserByName(String username);

    /**
     * 根据用户名查询当前用户，不包含密码
     *
     * @param username 用户名
     */
    CurrentUserInfo queryUserInfoByName(String username);

    /**
     * 根据用户名查询该用户名是否存在
     *
     * @param username 用户名
     */
    boolean isExist(String username);
}
