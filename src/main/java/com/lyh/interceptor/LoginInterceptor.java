package com.lyh.interceptor;

import com.lyh.utils.RedisUtil;
import com.lyh.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.Token;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @author lyh
 * @ClassName LoginInterceptor
 * @createTime 2021/12/16 16:51:00
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info(request.getRequestURI());
        log.info(request.getMethod());
        log.info(request.getHeader("token"));
        if(requestURI.contains("login") || requestURI.contains("article") || requestURI.contains("question") || requestURI.contains("qa")){
            return true;
        }
        if(TokenUtils.verify(request.getHeader("token"))){
            return true;
        }
        response.setStatus(401,"登录失效");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
