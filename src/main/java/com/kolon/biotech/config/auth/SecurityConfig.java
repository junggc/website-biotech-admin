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
    private final UserLogoutSuccessHandler logoutSuccessHandler;
    private final UserLogoutHandler logoutHandler;

    private final UserLoginExpiredStrategy userLoginExpiredStrategy;

    @Override
    public void configure(WebSecurity web) { // 4
        web.ignoring().antMatchers("/assets/**","/dext5editor/**","/dist/**","/error/**","/css/**", "/js/**", "/upload/**", "/img/**", "/h2-console/**").requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.httpFirewall(defaultHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeRequests() // 6
                .antMatchers("/sample","/login","/editorUpload","/error").permitAll()
                .anyRequest().authenticated() // ????????? ???????????? ????????? ????????? ?????? ?????? ????????? ????????? ?????? ??????
                //.and().requiresChannel().antMatchers("/main").requiresSecure()
                //.anyRequest().permitAll()
                .and()
                .formLogin() // 7
                .loginPage("/login") // ????????? ????????? ??????
                .loginProcessingUrl("/login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                //.defaultSuccessUrl("/loginSuccess") // ????????? ?????? ??? ??????????????? ??????
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout() // 8
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //.logoutSuccessUrl("/logoutSuccess") // ???????????? ????????? ??????????????? ??????
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)// ?????? ?????????
                .deleteCookies("JSESSIONID") /*?????? ??????*/
                .clearAuthentication(true) /*???????????? ??????*/
                .permitAll()
                .and()
                .sessionManagement()
                //.invalidSessionUrl("/logininvalid") //????????? ????????????????????? ???????????? ????????????  maxSessionsPreventsLogin true?????? ????????????.
                .maximumSessions(1) /* session ?????? ?????? */
                .maxSessionsPreventsLogin(false) /* ????????? ????????? ???????????? x, false ??? ?????? ?????? ????????? session ??????*/
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
