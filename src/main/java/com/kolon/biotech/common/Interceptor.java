package com.kolon.biotech.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.biotech.config.auth.XssRequest;
import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private HistoryService historyService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        log.info("[MYTEST] preHandle");

//        History history = null;
//        if(request.getRequestURI().contains("/noticeList")){
//            history = new History();
//            history.setUserId(request.getSession().getAttribute("loginId").toString());
//            history.setJobContent("공지사항관리에 접속했습니다.");
//            history.setId(Integer.valueOf(request.getSession().getAttribute("id").toString()));
//            history.setJobUrl(request.getRequestURI());
//            history.setUserName(request.getSession().getAttribute("username").toString());
//            history.setRequestIp(request.getRemoteAddr());
//        }else if(request.getRequestURI().contains("/mainvisualList")){
//            history = new History();
//            history.setUserId(request.getSession().getAttribute("loginId").toString());
//            history.setJobContent("메인비주얼관리에 접속했습니다.");
//            history.setId(Integer.valueOf(request.getSession().getAttribute("id").toString()));
//            history.setJobUrl(request.getRequestURI());
//            history.setUserName(request.getSession().getAttribute("username").toString());
//            history.setRequestIp(request.getRemoteAddr());
//        }else if(request.getRequestURI().contains("/mainpopList")){
//            history = new History();
//            history.setUserId(request.getSession().getAttribute("loginId").toString());
//            history.setJobContent("메인팝업관리에 접속했습니다.");
//            history.setId(Integer.valueOf(request.getSession().getAttribute("id").toString()));
//            history.setJobUrl(request.getRequestURI());
//            history.setUserName(request.getSession().getAttribute("username").toString());
//            history.setRequestIp(request.getRemoteAddr());
//        }else if(request.getRequestURI().contains("/qnaList")){
//            history = new History();
//            history.setUserId(request.getSession().getAttribute("loginId").toString());
//            history.setJobContent("문의하기관리에 접속했습니다.");
//            history.setId(Integer.valueOf(request.getSession().getAttribute("id").toString()));
//            history.setJobUrl(request.getRequestURI());
//            history.setUserName(request.getSession().getAttribute("username").toString());
//            history.setRequestIp(request.getRemoteAddr());
//        }else if(request.getRequestURI().contains("/userList")){
//            history = new History();
//            history.setUserId(request.getSession().getAttribute("loginId").toString());
//            history.setJobContent("관리자관리에 접속했습니다.");
//            history.setId(Integer.valueOf(request.getSession().getAttribute("id").toString()));
//            history.setJobUrl(request.getRequestURI());
//            history.setUserName(request.getSession().getAttribute("username").toString());
//            history.setRequestIp(request.getRemoteAddr());
//        }else if(request.getRequestURI().contains("/subsidiaryList")){
//            history = new History();
//            history.setUserId(request.getSession().getAttribute("loginId").toString());
//            history.setJobContent("계열사관리에 접속했습니다.");
//            history.setId(Integer.valueOf(request.getSession().getAttribute("id").toString()));
//            history.setJobUrl(request.getRequestURI());
//            history.setUserName(request.getSession().getAttribute("username").toString());
//            history.setRequestIp(request.getRemoteAddr());
//        }else if(request.getRequestURI().contains("/prbinfoList")){
//            history = new History();
//            history.setUserId(request.getSession().getAttribute("loginId").toString());
//            history.setJobContent("개인정보처리방침관리에 접속했습니다.");
//            history.setId(Integer.valueOf(request.getSession().getAttribute("id").toString()));
//            history.setJobUrl(request.getRequestURI());
//            history.setUserName(request.getSession().getAttribute("username").toString());
//            history.setRequestIp(request.getRemoteAddr());
//        }

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {

        log.info("[MYTEST] postHandle");
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object object,
            Exception ex
    ) throws Exception {


        log.info("[MYTEST] afterCompletion");
    }
}
