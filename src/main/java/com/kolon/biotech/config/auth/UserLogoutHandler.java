package com.kolon.biotech.config.auth;

import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.service.HistoryService;
import com.kolon.biotech.web.dto.MemberDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class UserLogoutHandler implements LogoutHandler {

    @Autowired
    private HistoryService historyService;

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
       if(authentication != null){
            MemberDto memberDto = (MemberDto)authentication.getPrincipal();

            History history = History.builder().userId(memberDto.getLoginId())
                    .jobContent("로그아웃 하였습니다.")
                    .jobFlag("J")
                    .requestDate(LocalDateTime.now())
                    .jobUrl(request.getRequestURI()).requestIp(request.getRemoteAddr())
                    .userName(memberDto.getUsername()).build();

            historyService.setWriteStroe(history);

        }
    }
}
