package com.kolon.biotech.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.biotech.web.dto.MailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Slf4j
@Component
public class MailContentBuilder {

    @Autowired
    private TemplateEngine templateEngine;

    public String build(MailDto mailDto){
        Context context = new Context();
        ObjectMapper objectMapper = new ObjectMapper();
        Map result = objectMapper.convertValue(mailDto, Map.class);
        context.setVariables(result);
        return templateEngine.process("mailform/email-template",context);
    }
}
