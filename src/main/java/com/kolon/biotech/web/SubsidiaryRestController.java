package com.kolon.biotech.web;

import com.kolon.biotech.domain.mainpop.Mainpop;
import com.kolon.biotech.domain.subsidiary.Subsidiary;
import com.kolon.biotech.service.SubsidiaryService;
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
public class SubsidiaryRestController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private SubsidiaryService subsidiaryService;


    @PostMapping("/subsidiaryEditProcess")
    public Subsidiary editProcess(@ModelAttribute Subsidiary obj) throws Exception{
        log.debug("=========noticeEditProcess============");
        Subsidiary _obj = subsidiaryService.setWriteStroe(obj);

        return _obj;
    }

    @PostMapping(value="/subsidiaryListAjax")
    public Page<Subsidiary> listAjax(@PageableDefault Pageable pageable, Model model){
        log.debug("=========userListAjax============");
        Page<Subsidiary> list = subsidiaryService.getList(pageable);

        return list;
    }

    @PostMapping("/subsidiaryDelete")
    public ResultJsonPagingDto delete(@RequestParam(value = "deleteList") List<Integer> deleteList){
        ResultJsonPagingDto dto = new ResultJsonPagingDto();
        try{
            subsidiaryService.delete(deleteList);

            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deleteok"));

        }catch(Exception e){
            log.error("delete",e.getMessage());
            dto.setSuccess(false);
            dto.setMessage(messageSourceAccessor.getMessage("deletefail"));

        }

        return dto;
    }

    @PostMapping("/subsidiaryOrderChange")
    public ResultJsonPagingDto orderChange(@RequestParam(value = "id") Integer id, @RequestParam(value="updown") String updown){
        log.debug("#############subsidiaryOrderChange#####################");
        log.debug("#########id="+id+"##########updn="+updown);
        ResultJsonPagingDto dto = new ResultJsonPagingDto();
        try{
            subsidiaryService.updateOrderSeq(id,updown);
            dto.setSuccess(true);
        }catch(Exception e){
            log.error("orderChange",e.getMessage());
            dto.setSuccess(false);
        }
        return dto;
    }
}
