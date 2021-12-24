package com.kolon.biotech.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.biotech.config.auth.XssRequest;
import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.service.HistoryService;
import com.kolon.biotech.web.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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

        boolean t = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof MemberDto){
            MemberDto memberDto = (MemberDto) authentication.getPrincipal();
            log.debug("#@####@###"+memberDto.getUsername());

            String jobContent = "";
            if("/noticeList".equals(request.getRequestURI())){
                jobContent = "공지사항관리에 접속했습니다.";
                t = true;
            }else if("/mainvisualList".equals(request.getRequestURI())){
                jobContent = "메인비주얼관리에 접속했습니다.";
                t = true;
            }else if("/mainpopList".equals(request.getRequestURI())){
                jobContent = "메인팝업관리에 접속했습니다.";
                t = true;
            }else if("/qnaList".equals(request.getRequestURI())){
                jobContent = "문의하기관리에 접속했습니다.";
                t = true;
            }else if("/userList".equals(request.getRequestURI())){
                jobContent = "관리자관리에 접속했습니다.";
                t = true;
            }else if("/subsidiaryList".equals(request.getRequestURI())){
                jobContent = "계열사관리에 접속했습니다.";
                t = true;
            }else if("/prbinfoList".equals(request.getRequestURI())){
                jobContent = "개인정보처리방침관리에 접속했습니다.";
                t = true;
            }else if("/grantHistory".equals(request.getRequestURI())){
                jobContent = "권한로그관리에 접속했습니다.";
                t = true;
            }else if("/jobHistory".equals(request.getRequestURI())){
                jobContent = "작업로그관리에 접속했습니다.";
                t = true;
            }

            if(t){
                History history = History.builder().userId(memberDto.getLoginId())
                        .jobContent(jobContent)
                        .jobFlag("J")
                        .jobUrl(request.getRequestURI()).requestIp(request.getRemoteAddr())
                        .requestDate(LocalDateTime.now())
                        .userName(memberDto.getUsername()).build();

                historyService.setWriteStroe(history);
            }


        }

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
