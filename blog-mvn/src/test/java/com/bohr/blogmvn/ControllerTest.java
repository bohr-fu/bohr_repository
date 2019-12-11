package com.bohr.blogmvn;

import com.alibaba.fastjson.JSONObject;
import com.bohr.blogmvn.entity.User;
import com.bohr.blogmvn.entity.UserStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
 * @date 2019/12/10 18:04
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogMvnApplication.class)
@WebAppConfiguration
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac; // 注入WebApplicationContext

    private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }



    @Test
    public void saveUserTest() throws Exception {
        User u = new User();
        u.setAccount("bohr");
        u.setNickname("付高帅");
        u.setPassword("123456");
        u.setAdmin(false);
        u.setCreateDate(new Date());
        u.setEmail("2794465700@qq.com");
        u.setMobilePhoneNumber("17722405575");
        u.setStatus(UserStatus.normal);

        String uri = "/users/create";

        MvcResult result = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(u)))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void findAllTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
