package com.kolon.biotech.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XssFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(XssFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
        // 필터적용
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        logger.debug("=======================================================");
        logger.debug( "XssFilter ");
        logger.debug("=======================================================");
        request.setCharacterEncoding("utf-8");

        chain.doFilter(new XssRequest(request), response);

    }

    @Override
    public void destroy(){

    }

    @Override
    public void init(FilterConfig fc) throws ServletException{

    }
}
