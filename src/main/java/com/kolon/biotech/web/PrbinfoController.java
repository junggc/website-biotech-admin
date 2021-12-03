package com.kolon.biotech.web;

import com.kolon.biotech.domain.mainvisual.Mainvisual;
import com.kolon.biotech.domain.prbinfo.Prbinfo;
import com.kolon.biotech.service.PrbinfoService;
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
public class PrbinfoController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private PrbinfoService prbinfoService;

    @GetMapping("prbinfoInfo")
    public String prbinfoInfo(Principal principal, @ModelAttribute Prbinfo obj, Model model){

        model.addAttribute("obj",prbinfoService.getInfo(obj.getId()));
        return "content/prbinfoInfo";
    }

    @GetMapping("prbinfoList")
    public String prbinfoList(@AuthenticationPrincipal MemberDto memberDto){
        return "content/prbinfoList";
    }

}
