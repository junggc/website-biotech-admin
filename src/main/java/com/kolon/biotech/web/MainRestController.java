package com.kolon.biotech.web;

import com.kolon.biotech.domain.mainvisual.Mainvisual;
import com.kolon.biotech.domain.notice.Notice;
import com.kolon.biotech.service.MainvisualService;
import com.kolon.biotech.web.dto.ResultJsonPagingDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MainRestController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MainvisualService mainvisualService;

    @PostMapping("mainVisualListAjax")
    public Page<Mainvisual> mainVisualListAjax(@PageableDefault Pageable pageable, Model model){
        Page<Mainvisual> mainVisual = mainvisualService.getMainvisualList(pageable);
        return mainVisual;
    }

    @PostMapping("/mainvisualEditProcess")
    public Mainvisual mainvisualEditProcess(@ModelAttribute Mainvisual mainvisual
            , @RequestParam(value = "pcImg", required = false) MultipartFile pcImg
            , @RequestParam(value = "moImg",required = false) MultipartFile moImg
            , @RequestParam(value = "mvFile", required = false) MultipartFile mvFile
            , @RequestParam(value = "pcImgDelete", required = false) String pcImgDelete
            , @RequestParam(value = "moImgDelete", required = false) String moImgDelete
            , @RequestParam(value = "mvFileDelete", required = false) String mvFileDelete
            , Model model) throws Exception{

        log.debug("############>>>"+pcImg.isEmpty());
        Mainvisual _mainMainvisual = mainvisualService.setWriteStroe(
                mainvisual
                , pcImg
                , moImg
                , mvFile
                , pcImgDelete
                , moImgDelete
                , mvFileDelete
        );

        return _mainMainvisual;
    }

}
