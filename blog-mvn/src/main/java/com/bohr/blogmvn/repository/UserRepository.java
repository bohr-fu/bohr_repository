package com.bohr.blogmvn.repository;

import com.bohr.blogmvn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户 持久话 层
 * @author Bohr Fu
 * @date 2019/12/10 16:15
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByAccount(String account);
}
