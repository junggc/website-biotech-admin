package com.kolon.biotech.web;

import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.service.HistoryService;
import com.kolon.biotech.web.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HistoryRestController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HistoryService historyService;

    @PostMapping(value="/historyListAjax")
    public Page<History> listAjax(@ModelAttribute SearchDto searchDto, @PageableDefault Pageable pageable, Model model) throws Exception{
        log.debug("=========historyListAjax============");
        Page<History> list = historyService.getList(searchDto, pageable);

        return list;
    }

}
