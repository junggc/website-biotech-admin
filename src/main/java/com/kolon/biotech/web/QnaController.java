package com.kolon.biotech.web;

import com.kolon.biotech.domain.qna.Qna;
import com.kolon.biotech.domain.subsidiary.Subsidiary;
import com.kolon.biotech.service.QnaService;
import com.kolon.biotech.web.dto.MemberDto;
import com.kolon.biotech.web.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class QnaController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private QnaService qnaService;

    @RequestMapping("qnaInfo")
    public String qnaInfo(@ModelAttribute Qna obj, @ModelAttribute SearchDto searchDto, Model model){

        model.addAttribute("searchDto",searchDto);
        model.addAttribute("obj",qnaService.getInfo(obj.getId()));
        return "content/qnaInfo";
    }

    @RequestMapping("qnaList")
    public String qnaList(@AuthenticationPrincipal MemberDto memberDto, @ModelAttribute SearchDto searchDto, Model model){
        model.addAttribute("searchDto",searchDto);
        return "content/qnaList";
    }

}
