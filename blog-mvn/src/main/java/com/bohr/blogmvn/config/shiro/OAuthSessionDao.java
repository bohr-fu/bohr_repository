package com.bohr.blogmvn.config.shiro;

import com.bohr.blogmvn.common.cache.RedisManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * session操作类，Shrio用户信息保存在session
 *
 * 配置session保存在Redis
 *
 * 实现InitializingBean接口 在afterPropertiesSet方法中保证初始化前先校验StringRedisTemplate
 * @author Bohr Fu
 * @date 2019/12/11 11:06
 * @Version 1.0
 */
public class OAuthSessionDao extends CachingSessionDAO implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(OAuthSessionDao.class);

    @Setter
    @Getter
    private RedisManager redisManager;

    @Override
    protected Serializable doCreate(Session session) {

        // 生成sessionId并与session绑定
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);

        logger.info(sessionId.toString());

        // 存储在redis
        redisManager.set(sessionId.toString(),session,RedisManager.DEFAULT_EXPIRE);
        return sessionId;
    }

    @Override
    protected void doUpdate(Session session) {

        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }
        logger.info(session.getId().toString());
        redisManager.set(session.getId().toString(), session, RedisManager.DEFAULT_EXPIRE);
    }

    @Override
    protected void doDelete(Session session) {
        redisManager.delete(session.getId().toString());
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        logger.info(serializable.toString());
        return redisManager.get(serializable.toString(), Session.class);
    }

    /**
     * 校验StringRedisTemplate
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == this.redisManager) {
            logger.error("StringRedisTemplate should be not null.");
        }
    }
}
