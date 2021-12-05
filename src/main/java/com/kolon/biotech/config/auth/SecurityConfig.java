package com.kolon.biotech.config.auth;

import com.kolon.biotech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserLoginSuccessHandler successHandler;
    private final UserLoginFailureHandler failureHandler;
    private final UserService userService; // 3

    private final UserLoginExpiredStrategy userLoginExpiredStrategy;

    @Override
    public void configure(WebSecurity web) { // 4
        web.ignoring().antMatchers("/assets/**","/dext5editor/**","/dist/**","/error/**","/css/**", "/js/**", "/upload/**", "/img/**", "/h2-console/**").requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.httpFirewall(defaultHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionFixation().changeSessionId()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeRequests() // 6
                .antMatchers("/sample","/login").permitAll()
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                //.anyRequest().permitAll()
                .and()
                .formLogin() // 7
                .loginPage("/login") // 로그인 페이지 링크
                .loginProcessingUrl("/login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .defaultSuccessUrl("/loginSuccess") // 로그인 성공 후 리다이렉트 주소
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout() // 8
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logoutSuccess") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true)// 세션 날리기
                .deleteCookies("JSESSIONID") /*쿠키 제거*/
                .clearAuthentication(true) /*권한정보 제거*/
                .permitAll()
                .and()
                .sessionManagement()
                //.invalidSessionUrl("/logininvalid") //세션이 유효하징낳을떄 이동하는 페이지는  maxSessionsPreventsLogin true일때 사용한다.
                .maximumSessions(1) /* session 허용 갯수 */
                .maxSessionsPreventsLogin(false) /* 동일한 사용자 로그인시 x, false 일 경우 기존 사용자 session 종료*/
                .expiredSessionStrategy(this.userLoginExpiredStrategy.setDefaultUrl("/logininvalid"))

        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }


}