package com.bohr.blogmvn.service.impl;

import com.bohr.blogmvn.common.util.PasswordHelper;
import com.bohr.blogmvn.entity.User;
import com.bohr.blogmvn.repository.UserRepository;
import com.bohr.blogmvn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Bohr Fu
 * @date 2019/12/10 16:22
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    public final static String AVATAR_PATH = "/static/user/user_";

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * 保存用户  返回主键
     * @param user
     * @return
     */
    @Override
    public Long saveUser(User user) {
        PasswordHelper.encryptPassword(user);
        int index = new Random().nextInt(6) + 1;
        String avatar = "/static/user/user_" + index + ".png";

        user.setAvatar(avatar);
        return userRepository.save(user).getId();
    }

    /**
     * 修改 用户信息
     * @param user
     * @return
     */
    @Override
    public Long updateUser(User user) {
        Optional<User> oldUser =userRepository.findById(user.getId());
        oldUser.ifPresent(user1 -> userRepository.save(user));
        return user.getId();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
