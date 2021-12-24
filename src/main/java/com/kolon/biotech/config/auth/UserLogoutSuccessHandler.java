package com.kolon.biotech.config.auth;

import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.domain.user.Member;
import com.kolon.biotech.service.HistoryService;
import com.kolon.biotech.web.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class UserLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private HistoryService historyService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
//        if(authentication != null){
//            MemberDto memberDto = (MemberDto)authentication.getPrincipal();
//
//            History history = History.builder().userId(memberDto.getLoginId())
//                    .jobContent("로그아웃 하였습니다.")
//                    .jobFlag("J")
//                    .requestDate(LocalDateTime.now())
//                    .jobUrl(request.getRequestURI()).requestIp(request.getRemoteAddr())
//                    .userName(memberDto.getUsername()).build();
//
//            historyService.setWriteStroe(history);
//
//        }

        setDefaultTargetUrl("/login");
        super.onLogoutSuccess(request, response, authentication);
    }
}
