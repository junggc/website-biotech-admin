package com.kolon.biotech.web;

import com.kolon.biotech.domain.mainvisual.Mainvisual;
import com.kolon.biotech.domain.notice.Notice;
import com.kolon.biotech.service.MainvisualService;
import com.kolon.biotech.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.LocaleResolver;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MainvisualService mainvisualService;

    @GetMapping({"/","/main"})
    public String getAdmin(@AuthenticationPrincipal MemberDto memberDto, Model model) {
        return "content/main";
    }

    @GetMapping("/hello")
    public String getHello(Model model) {
        return "/content/hello";
    }

    @GetMapping("mainvisualInfo")
    public String mainvisualInfo(@AuthenticationPrincipal MemberDto memberDto, @ModelAttribute Mainvisual mainvisual, Model model){

        model.addAttribute("mainvisual",mainvisualService.getMainvisual(mainvisual.getId()));
        return "content/mainVisualInfo";
    }

    @GetMapping("mainvisualList")
    public String mainvisualList(@AuthenticationPrincipal MemberDto memberDto){
        return "content/mainVisualList";
    }



}
