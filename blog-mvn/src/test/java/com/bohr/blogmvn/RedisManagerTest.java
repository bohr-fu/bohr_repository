package com.bohr.blogmvn;

import com.alibaba.fastjson.JSONObject;
import com.bohr.blogmvn.common.cache.RedisManager;
import com.bohr.blogmvn.entity.User;
import com.bohr.blogmvn.entity.UserStatus;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Bohr Fu
 * @Date 2019/12/10 14:31
 * @Version 1.0
 */
public class RedisManagerTest extends BlogMvnApplicationTests {

    @Autowired
    private RedisManager redisManager;

    @Test
    public void setTest(){
        String key  = "fzb";
        String value = "除却巫山不是云";
        redisManager.set(key,value);
    }

    @Test
    public void getTest(){
        System.out.println(redisManager.get("fzb",String.class));
    }

    @Test
    public void deleteTest(){
        String key = "fzb";
        redisManager.delete(key);
        Object o = redisManager.get(key);
        System.out.println(o);
    }

}
