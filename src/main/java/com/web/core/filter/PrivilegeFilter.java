package com.web.core.filter;

import com.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限过滤器
 * @author 杜延雷
 * @date 2016/06/18
 */
public class PrivilegeFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(PrivilegeFilter.class) ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request ;
        HttpServletResponse resp = (HttpServletResponse) response ;
        String requestUri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String url = requestUri.substring(contextPath.length());

        // 不需要拦截路径
        if (url.indexOf("/login") > -1) {
            chain.doFilter(request, response);
        } else {
            //用户没有登录
            if (null == WebUtils.getUser(req)) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
