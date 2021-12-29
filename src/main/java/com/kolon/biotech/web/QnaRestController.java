package com.kolon.biotech.web;

import com.kolon.biotech.domain.notice.Noticefile;
import com.kolon.biotech.domain.qna.Qna;
import com.kolon.biotech.domain.subsidiary.Subsidiary;
import com.kolon.biotech.service.QnaService;
import com.kolon.biotech.web.dto.DownloadView;
import com.kolon.biotech.web.dto.ResultJsonPagingDto;
import com.kolon.biotech.web.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class QnaRestController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private QnaService qnaService;


    @PostMapping("/qnaEditProcess")
    public Qna editProcess(@ModelAttribute Qna obj, @RequestParam(value = "file", required = false) MultipartFile[] noticefile, @RequestParam(value = "deletefilenum", required = false) List<Integer> deleteFileList) throws Exception{
        log.debug("=========noticeEditProcess============");
        Qna _obj = qnaService.setWriteStroe(obj, noticefile, deleteFileList);

        return _obj;
    }

    @PostMapping(value="/qnaListAjax")
    public Page<Qna> listAjax(@PageableDefault Pageable pageable,@ModelAttribute SearchDto searchDto, Model model){
        log.debug("=========qnaListAjax============");
        Page<Qna> list = qnaService.getList(searchDto,pageable);

        return list;
    }

    @PostMapping("/qnaDelete")
    public ResultJsonPagingDto delete(@RequestParam(value = "deleteList") List<Integer> deleteList){
        ResultJsonPagingDto dto = new ResultJsonPagingDto();
        try{
            qnaService.delete(deleteList);

            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deleteok"));

        }catch(Exception e){

            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deletefail"));

        }

        return dto;
    }

    @GetMapping("/download")
    public void download(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception{

        Qna qna = qnaService.getInfo(id);

        if(qna != null && !ObjectUtils.isEmpty(qna) && qna.getId() > 0){

            try{

                DownloadView fileDown = new DownloadView(); //파일다운로드 객체생성
                fileDown.filDown(request, response, qna.getFilePath() , qna.getFileName()); //파일다운로드

            }catch(Exception e){
                log.error("filedownerror",e.getMessage());
                throw new Exception();
            }

        }else{
            log.error("filedownerror","null");
            throw new Exception();
        }

    }
}
