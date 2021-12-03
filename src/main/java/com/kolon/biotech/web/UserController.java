package com.kolon.biotech.web;

import com.kolon.biotech.domain.user.Role;
import com.kolon.biotech.domain.user.Member;
import com.kolon.biotech.service.UserService;
import com.kolon.biotech.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    private final UserService userService;

    //로그아웃 결과 페이지
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    //로그인 페이지 이동
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("isLocked",request.getAttribute("isLocked"));
        model.addAttribute("isLockedMsg",request.getAttribute("isLockedMsg"));
        return "content/login/login";
    }

    //회원가입 페이지 이동
    @GetMapping(value = "/userInfo")
    public String userInfo(@ModelAttribute Member member, Model model) {

        Member user = userService.getUser(member);
        model.addAttribute("member",user);
        return "content/userInfo";
    }

    //회원가입 처리
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute Member user){
        userService.joinUser(user);
        return "redirect:/userList";
    }

    //로그인 결과 페이지
    @GetMapping("/loginSuccess")
    public String loginSuccess(){
        return "redirect:/main";
    }

    @GetMapping("/userList")
    public String userList(@AuthenticationPrincipal MemberDto memberDto){

        return "content/userList";
    }
}


