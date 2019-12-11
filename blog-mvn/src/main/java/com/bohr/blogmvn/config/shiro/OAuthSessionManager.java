package com.bohr.blogmvn.config.shiro;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 *
 *  从请求头获取sessionId( Oauth-Token)
 * @author Bohr Fu
 * @date 2019/12/11 11:35
 * @Version 1.0
 */
public class OAuthSessionManager extends DefaultSessionManager {

    /**
     * 请求头 键值key
     */
    public static final String OAUTH_TOKEN = "Oauth-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public OAuthSessionManager() {
        super();
    }

    protected Serializable getSessionId(ServletRequest request, ServletResponse response){
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String sessionId = httpRequest.getHeader(OAUTH_TOKEN);

        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

        return sessionId;

    }
}
