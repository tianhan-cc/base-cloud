package com.tianhan.cloud.common.auth;

import com.tianhan.cloud.common.core.SystemConstant;
import jodd.util.StringUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author NieAnTai
 * @Date 2021/4/19 9:02 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description WebMVC Filter 拦截器
 **/
public class AuthContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        String token = null;
        try {
            token = obtainToke(request);
            if (StringUtil.isNotBlank(token)) {
                AuthContext.setJwtAttributesHolder(token);
            }
            filter.doFilter(request, response);
        } finally {
            if (StringUtil.isNotBlank(token)) {
                // 清空本地线程记录
                AuthContext.remove();
            }
        }
    }

    private static String obtainToke(HttpServletRequest request) {
        String token = request.getHeader(SystemConstant.TOKEN_KEY);
        if (StringUtil.isBlank(token) && StringUtil.isNotBlank(request.getQueryString())) {
            String queryStr = request.getQueryString();
            String[] queryArr = queryStr.split("&");
            for (String q : queryArr) {
                if (q.startsWith(SystemConstant.TOKEN_KEY)) {
                    token = q.split("=")[1];
                    break;
                }
            }
        }
        return token;
    }
}
