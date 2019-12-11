package com.bohr.blogmvn.service;

import com.bohr.blogmvn.entity.User;

import java.util.List;

/**
 *
 * 用户 service 层
 * @author Bohr Fu
 * @date 2019/12/10 16:18
 */
public interface UserService {

    List<User> findAll();

    User getUserByAccount(String account);

    User getUserById(Long Id);

    Long saveUser(User user);

    Long updateUser(User user);

    void deleteUserById(Long id);
}
