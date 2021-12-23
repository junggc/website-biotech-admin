package com.kolon.biotech.config.auth;

import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.domain.user.Member;
import com.kolon.biotech.domain.user.MemberRepository;
import com.kolon.biotech.service.HistoryService;
import com.kolon.biotech.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final MemberRepository memberRepository;

    @Autowired
    private HistoryService historyService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("====onAuthenticationSuccess====");
        // 로그인 5회 실패 전 로그인 성공할경우 실패 횟수 리셋을 위해 엔티티 조회
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails)principal;
//        String currentUser = userDetails.getUsername();
//
        MemberDto memberDto = (MemberDto)authentication.getPrincipal();
        String currentUser = memberDto.getLoginId();

        Member findAccount = memberRepository.findByLoginId(currentUser).get();
        //90일 경과
        //log.debug("#####??DDD?D?D?D?D?"+ChronoUnit.DAYS.between(LocalDateTime.now(),LocalDateTime.now()));
        LocalDateTime pwdDate = findAccount.getPasswordChangeDate() != null ? findAccount.getPasswordChangeDate() : findAccount.getRegDtime();
        if(ChronoUnit.DAYS.between(pwdDate,LocalDateTime.now()) >= 90){
            findAccount.setBlocked("true");


            History history = History.builder().userId(findAccount.getLoginId())
                    .jobContent("비밀번호가 3개월이 지나 로그인 할 수 없습니다.")
                    .jobFlag("J")
                    .requestDate(LocalDateTime.now())
                    .jobUrl(request.getRequestURI()).requestIp(request.getRemoteAddr())
                    .userName(findAccount.getName()).build();

            historyService.setWriteStroe(history);



            throw new LockedException("비밀번호가 3개월이 지나 로그인 할 수 없습니다.\\n마스터 관리자에게 문의하세요.");
        }

        if(findAccount.getFailCount() >= 5){
            // 아래 예외로 인해 로그인 실패가 발생하고, 로그인 실패 핸들러 호출됨

            History history = History.builder().userId(findAccount.getLoginId())
                    .jobContent("계정이 잠겼습니다.")
                    .jobFlag("J")
                    .requestDate(LocalDateTime.now())
                    .jobUrl(request.getRequestURI()).requestIp(request.getRemoteAddr())
                    .userName(findAccount.getName()).build();

            historyService.setWriteStroe(history);

            throw new LockedException("계정이 잠겼습니다.\\n마스터 관리자에게 문의하세요.");
        }else{

            History history = History.builder().userId(findAccount.getLoginId())
                    .jobContent("로그인 하였습니다.")
                    .jobFlag("J")
                    .requestDate(LocalDateTime.now())
                    .jobUrl(request.getRequestURI()).requestIp(request.getRemoteAddr())
                    .userName(findAccount.getName()).build();

            historyService.setWriteStroe(history);

            findAccount.setFailCount(0);
            findAccount.setLoginDate(LocalDateTime.now());
        }

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // 인증이 필요한 리소스에 접근하려다 로그인 화면으로 넘어간경우
        if(savedRequest != null){
            redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        }else{  // 직접 로그인 페이지로 이동해서 들어온경우 메인페이지로 리다이렉트
            redirectStrategy.sendRedirect(request, response, "/main");
        }
    }
}
