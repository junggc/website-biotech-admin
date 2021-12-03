package com.kolon.biotech.web.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SampleController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/sample")
    public String sampleLan(HttpServletRequest request, @RequestParam HashMap<String, Object> map, Model model){

        log.info("messageSourceAccessor : {}", request.getParameter("name"));
        log.info("messageSourceAccessor : {}", map.get("name"));
        log.info("messageSourceAccessor : {}", messageSourceAccessor.getMessage("hello"));
        log.info("messageSourceAccessor with LocaleResolver : {}", messageSourceAccessor.getMessage("hello", localeResolver.resolveLocale(request)));

        model.addAttribute("name",map.get("name"));

        return "sample";
    }

    @GetMapping("/home")
    public String home(@RequestParam HashMap<String, Object> map, HttpServletRequest request, Model model){

        log.info("messageSourceAccessor : {}", request.getParameter("name"));
        log.info("messageSourceAccessor : {}", map.get("name"));
        log.info("messageSourceAccessor : {}", messageSourceAccessor.getMessage("hello"));
        log.info("messageSourceAccessor with LocaleResolver : {}", messageSourceAccessor.getMessage("hello", localeResolver.resolveLocale(request)));

        model.addAttribute("name",map.get("name"));

        return "content/home";
    }
}
