package com.bohr.blogmvn.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.bohr.blogmvn.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * 用户类
 * @author Bohr Fu
 * @Date 2019/12/10 15:51
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity<Long> {

    private static final long serialVersionUID = 6214877777232016006L;

    @NotBlank
    @Column(name = "account", unique = true, length = 10)
    private String account;

    /**
     * 使用md5(username + original password + salt)加密存储
     */
    @NotBlank
    @Column(name = "password", length = 64)
    private String password;

    /**
     * 头像
     */
    private String avatar;

    @Column(name = "email", unique = true, length = 20)
    private String email;  // 邮箱

    @NotBlank
    @Column(name = "nickname", length = 10)
    private String nickname;

    @Column(name = "mobile_phone_number", length = 20)
    private String mobilePhoneNumber;


    /**
     * 加密密码时使用的种子
     */
    private String salt;


    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


    /**
     * 最后一次登录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    /**
     * 系统用户的状态
     */
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.normal;

    /**
     * 是否是管理员
     */
    private Boolean admin = false;

    /**
     * 逻辑删除flag
     */
    private Boolean deleted = Boolean.FALSE;

}
