package com.kolon.biotech.config.auth;

import com.kolon.biotech.domain.user.Member;
import com.kolon.biotech.domain.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    private final MemberRepository memberRepository;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("====onAuthenticationFailure====");
        if(exception instanceof BadCredentialsException) { // 그냥 아이디, 비밀번호가 일치하지 않아서 진입했을경우
            String loginId = request.getParameter("loginId");
            // 로그인 실패 카운트 수정하기 위해 엔티티 조회
            try{
                Optional<Member> memberWrapper = memberRepository.findByLoginId(loginId);

                if (memberWrapper == null || !memberWrapper.isPresent()) {
                    //throw new IllegalArgumentException("잘못된 요청입니다.");
                    request.setAttribute("isLocked", true);
                    request.setAttribute("isLockedMsg", "요청하신 사용자를 찾을 수 없습니다.");
                }else{

                    Member findMember = memberWrapper.get();
log.debug("###################findMember.getBlocked()="+findMember.getBlocked());
                    if("true".equals(findMember.getBlocked())){
                        request.setAttribute("isLocked", true);
                        request.setAttribute("isLockedMsg", "계정이 잠겼습니다. 관리자에 문의바랍니다.");
                    }else{
                        log.debug("###################findMember.getBlocked()1="+findMember.getBlocked());
                        findMember.setFailCount((findMember.getFailCount() == null ? 0 : findMember.getFailCount()) +1);

                        log.debug("###################findMember.getFailCount()2="+findMember.getFailCount());
                        if (findMember.getFailCount() >= 5) {
                            findMember.setBlocked("true");
                            request.setAttribute("isLocked", true);
                            request.setAttribute("isLockedMsg", "5회 실패하여 계정이 잠겼습니다. 관리자에 문의바랍니다.");
                            log.debug("###################findMember.getFailCount()3="+findMember.getFailCount());

                        } else {
                            request.setAttribute("isLocked", true);
                            request.setAttribute("isLockedMsg", "아이디 비밀번호 확인바랍니다.");
                            log.debug("###################findMember.getFailCount()4="+findMember.getFailCount());
                        }
                    }

                }
            }catch(IllegalArgumentException e){
                request.setAttribute("isLocked", true);
                request.setAttribute("isLockedMsg", "요청하신 사용자를 찾을 수 없습니다.");
            }


        }else if(exception instanceof LockedException){ // LoginSuccessHandler에서 LockedException발생시 넘어 온 경우
            log.debug("############"+"사용자 못찾음2222222221111111111");
            request.setAttribute("isLocked", true);
            request.setAttribute("isLockedMsg", exception.getMessage());
        }else if(exception instanceof UsernameNotFoundException){
            System.out.println("사용자 못찾음");
            log.debug("############"+"사용자 못찾음");
            System.out.println(exception.getMessage());
        }else if(exception instanceof Exception){
            if("No value present".equals(exception.getMessage())){
                System.out.println("사용자 못찾음2222");
                log.debug("############"+"사용자 못찾음222222222");
            }
        }else{
            log.debug("############"+"사용자 못찾음2222222221111111111elseselse");
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");
        requestDispatcher.forward(request, response);
    }
}
