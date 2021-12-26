package com.kolon.biotech.config.custom;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;


@Component
public class CommUtils {

    public String getUnEscHtml(String target){
        return StringEscapeUtils.unescapeHtml4(target);
    }
}
