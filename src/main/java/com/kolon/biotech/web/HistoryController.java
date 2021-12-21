package com.kolon.biotech.web;


import com.kolon.biotech.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HistoryController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping(value="/jobHistory")
    public String jobHistory(@AuthenticationPrincipal MemberDto memberDto, Model model){
        return "content/jobHistory";
    }

    @GetMapping(value="/grantHistory")
    public String grantHistory(@AuthenticationPrincipal MemberDto memberDto, Model model){
        return "content/grantHistory";
    }

}
