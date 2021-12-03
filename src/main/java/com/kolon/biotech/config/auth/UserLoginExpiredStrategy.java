package com.kolon.biotech.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserLoginExpiredStrategy implements SessionInformationExpiredStrategy {

    private String defaultUrl;

    public String getDefaultUrl() {
        return defaultUrl;
    }

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
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ajax REquest Denied (Session Expired)");
                SecurityContextHolder.clearContext();
                throw new AccessDeniedException("Ajax request time out.");
            }else{
                log.debug("################이건 찍히긴 찍히냐2???"+event.getSessionInformation().isExpired());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");

                request.setAttribute("isLocked", true);
                request.setAttribute("isLockedMsg", "중복 로그인으로 로그아웃 처리되었습니다.\\n재 로그인 후 이용해주세요.");

                requestDispatcher.forward(request, response);
            }

        }

        //response.sendRedirect(defaultUrl);
    }
}
