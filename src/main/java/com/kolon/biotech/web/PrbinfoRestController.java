package com.kolon.biotech.web;

import com.kolon.biotech.domain.mainpop.Mainpop;
import com.kolon.biotech.domain.prbinfo.Prbinfo;
import com.kolon.biotech.service.PrbinfoService;
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
public class PrbinfoRestController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private PrbinfoService prbinfoService;

    @PostMapping("/prbinfoEditProcess")
    public Prbinfo editProcess(@ModelAttribute Prbinfo obj, @RequestParam(value = "file", required = false) MultipartFile[] noticefile, @RequestParam(value = "deletefilenum", required = false) List<Integer> deleteFileList) throws Exception{
        log.debug("=========noticeEditProcess============");
        Prbinfo _obj = prbinfoService.setWriteStroe(obj, noticefile, deleteFileList);

        return _obj;
    }

    @PostMapping(value="/prbinfoListAjax")
    public Page<Prbinfo> listAjax(@PageableDefault Pageable pageable, Model model){
        log.debug("=========userListAjax============");
        Page<Prbinfo> list = prbinfoService.getList(pageable);

        return list;
    }

    @PostMapping("/prbinfoDelete")
    public ResultJsonPagingDto delete(@RequestParam(value = "deleteList") List<Integer> deleteList){
        ResultJsonPagingDto dto = new ResultJsonPagingDto();
        try{
            prbinfoService.delete(deleteList);

            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deleteok"));

        }catch(Exception e){

            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deletefail"));

        }

        return dto;
    }
}
