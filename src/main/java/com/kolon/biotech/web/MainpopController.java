package com.kolon.biotech.web;

import com.kolon.biotech.domain.mainpop.Mainpop;
import com.kolon.biotech.domain.mainvisual.Mainvisual;
import com.kolon.biotech.service.MainpopService;
import com.kolon.biotech.web.dto.MemberDto;
import com.kolon.biotech.web.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainpopController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MainpopService mainpopService;

    @RequestMapping("mainpopInfo")
    public String mainpopInfo(Principal principal, @ModelAttribute SearchDto searchDto, @ModelAttribute Mainpop obj, Model model){

        model.addAttribute("searchDto",searchDto);
        model.addAttribute("mainpop",mainpopService.getInfo(obj.getId()));
        return "content/mainpopInfo";
    }

    @RequestMapping("mainpopList")
    public String mainpopList(@AuthenticationPrincipal MemberDto memberDto, @ModelAttribute SearchDto searchDto, Model model){
        model.addAttribute("searchDto",searchDto);
        return "content/mainpopList";
    }
}
