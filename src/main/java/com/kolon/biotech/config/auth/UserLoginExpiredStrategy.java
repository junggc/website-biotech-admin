package com.kolon.biotech.config.auth;

import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.service.HistoryService;
import com.kolon.biotech.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserLoginExpiredStrategy implements SessionInformationExpiredStrategy {

    private String defaultUrl;

    public String getDefaultUrl() {
        return defaultUrl;
    }

    @Autowired
    private HistoryService historyService;

    public UserLoginExpiredStrategy setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
        return this;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        log.debug("################이건 찍히긴 찍히냐???"+event.getSessionInformation().isExpired());
        if(event.getSessionInformation().isExpired()){

            HttpServletRequest request = event.getRequest();
            HttpServletResponse response = event.getResponse();
            if( Boolean.TRUE.toString().equals(request.getHeader("AJAX"))){
                log.debug("################이건 찍히긴 찍히냐1???"+event.getSessionInformation().isExpired());
                MemberDto memberDto= (MemberDto)event.getSessionInformation().getPrincipal();

                History history = History.builder().userId(memberDto.getLoginId())
                        .jobContent("Ajax REquest Denied (Session Expired)")
                        .jobFlag("J")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI()).requestIp(request.getRemoteAddr()).build();
                historyService.setWriteStroe(history);

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ajax REquest Denied (Session Expired)");
                SecurityContextHolder.clearContext();
                throw new AccessDeniedException("Ajax request time out.");
            }else{
                log.debug("################이건 찍히긴 찍히냐2???"+event.getSessionInformation().isExpired());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");

                MemberDto memberDto= (MemberDto)event.getSessionInformation().getPrincipal();

                History history = History.builder().userId(memberDto.getLoginId())
                        .jobContent("중복 로그인으로 로그아웃 처리되었습니다. 다시 로그인 후 이용해주세요.")
                        .jobFlag("J")
                        .requestDate(LocalDateTime.now())
                        .jobUrl(request.getRequestURI()).requestIp(request.getRemoteAddr()).build();
                historyService.setWriteStroe(history);

                request.setAttribute("isLocked", true);
                request.setAttribute("isLockedMsg", "중복 로그인으로 로그아웃 처리되었습니다.\\n다시 로그인 후 이용해주세요.");

                requestDispatcher.forward(request, response);
            }

        }

        //response.sendRedirect(defaultUrl);
    }
}
