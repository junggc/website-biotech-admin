package com.kolon.biotech.web.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
public class Sample2Controller {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @PostMapping("/sample2")
    public HashMap<String, Object> sampleLan2(@RequestBody HashMap<String, Object> map, HttpServletRequest request, Model model){

        log.info("messageSourceAccessor2 : {}", request.getParameter("name"));
        log.info("messageSourceAccessor2 : {}", map.get("name"));
        log.info("messageSourceAccessor2 : {}", messageSourceAccessor.getMessage("hello"));
        log.info("messageSourceAccessor2 with LocaleResolver : {}", messageSourceAccessor.getMessage("hello", localeResolver.resolveLocale(request)));


        return map;
    }
}
