package com.kolon.biotech.web;

import com.kolon.biotech.domain.mainpop.Mainpop;
import com.kolon.biotech.domain.subsidiary.Subsidiary;
import com.kolon.biotech.service.SubsidiaryService;
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
import org.springframework.web.servlet.LocaleResolver;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SubsidiaryController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private SubsidiaryService subsidiaryService;

    @GetMapping("subsidiaryInfo")
    public String subsidiaryInfo(Principal principal, @ModelAttribute SearchDto searchDto, @ModelAttribute Subsidiary obj, Model model){
        model.addAttribute("searchDto",searchDto);
        model.addAttribute("obj",subsidiaryService.getInfo(obj.getId()));
        return "content/subsidiaryInfo";
    }

    @GetMapping("/subsidiaryList")
    public String subsidiaryList(@AuthenticationPrincipal MemberDto memberDto, @ModelAttribute SearchDto searchDto,Model model){
        model.addAttribute("searchDto",searchDto);
        return "content/subsidiaryList";
    }
}
