package com.kolon.biotech.web;

import com.kolon.biotech.domain.notice.Notice;
import com.kolon.biotech.service.NoticeService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NoticeController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/noticeEdit")
    public String noticeEdit(@ModelAttribute Notice notice, Model model){

        model.addAttribute("notice",noticeService.noticeSaveAndUpdate(notice));

        return "";
    }

    @GetMapping("/noticeInfo")
    public String noticeInfo(Principal principal, @ModelAttribute SearchDto searchDto, @ModelAttribute Notice notice, Model model){
        log.debug("======noticeInfo=======");

        model.addAttribute("searchDto",searchDto);
        model.addAttribute("notice",noticeService.getNotice(notice.getId()));
        return "content/noticeInfo";
    }

    @GetMapping("/noticeList")
    public String noticeList(@AuthenticationPrincipal MemberDto memberDto, @ModelAttribute SearchDto searchDto,Model model){
        model.addAttribute("searchDto",searchDto);
        return "content/noticeList";
    }

}
