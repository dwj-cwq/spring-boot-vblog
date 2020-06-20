package com.dwj.vblogold.service.impl;

import com.dwj.vblogold.entity.CurrentUserInfo;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.entity.UserEntity;
import com.dwj.vblogold.repository.UserRepository;
import com.dwj.vblogold.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public PageList<UserEntity> queryUserList(String username, Integer offset, Integer limit) {
        PageList<UserEntity> userPageList = new PageList<>();
        List<UserEntity> userList = userRepository.queryUserList(username, offset - 1, limit);
        int total = userRepository.queryUserTotal(username);
        userPageList.setRows(userList);
        userPageList.setTotal(total);
        return userPageList;
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        userEntity.setCreateTime(new Date());
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity queryUserByName(String username) {
        return userRepository.queryUserByName(username);
    }

    @Override
    public CurrentUserInfo queryUserInfoByName(String username) {
        return userRepository.queryUserInfoByName(username);
    }

}
