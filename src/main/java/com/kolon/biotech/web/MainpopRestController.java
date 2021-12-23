package com.kolon.biotech.web;

import com.kolon.biotech.domain.mainpop.Mainpop;
import com.kolon.biotech.domain.notice.Notice;
import com.kolon.biotech.service.MainpopService;
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
public class MainpopRestController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MainpopService mainpopService;

    @PostMapping("/mainpopEditProcess")
    public Mainpop mainpopEditProcess(@ModelAttribute Mainpop mainpop, @RequestParam(value = "file", required = false) MultipartFile noticefile, @RequestParam(value = "deletefilenum", required = false) String deleteFile) throws Exception{
        log.debug("=========noticeEditProcess============");
        Mainpop _mainpop = mainpopService.setWriteStroe(mainpop, noticefile, deleteFile);

        return _mainpop;
    }

    @PostMapping(value="/mainpopListAjax")
    public Page<Mainpop> mainpopListAjax(@ModelAttribute Mainpop mainpop, @PageableDefault Pageable pageable, Model model){
        log.debug("=========userListAjax============");
        Page<Mainpop> list = mainpopService.getList(mainpop, pageable);

        return list;
    }

    @PostMapping(value="/mainpopDelete")
    public ResultJsonPagingDto  mainpopDelete(@RequestParam(value="deleteList") List<Integer> deleteList){
        ResultJsonPagingDto dto = new ResultJsonPagingDto();
        try{
            mainpopService.delete(deleteList);

            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deleteok"));
        }catch(Exception e){
            e.printStackTrace();
            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deletefail"));
        }

        return dto;
    }

}
