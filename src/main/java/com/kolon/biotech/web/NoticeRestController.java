package com.kolon.biotech.web;

import com.kolon.biotech.domain.notice.Notice;
import com.kolon.biotech.domain.notice.Noticefile;
import com.kolon.biotech.domain.user.Member;
import com.kolon.biotech.service.NoticeService;
import com.kolon.biotech.web.dto.ResultJsonPagingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
public class NoticeRestController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/noticeEditProcess")
    public Notice noticeEditProcess(@ModelAttribute Notice notice, @RequestParam(value = "file", required = false) MultipartFile[] noticefile, @RequestParam(value = "deletefilenum", required = false) List<Integer> deleteFileList) throws Exception{
        log.debug("=========noticeEditProcess============");
        Notice _notice = noticeService.setWriteStroeNotice(notice, noticefile, deleteFileList);

        return _notice;
    }

    @PostMapping(value="/noticeListAjax")
    public Page<Notice> noticeListAjax(@PageableDefault Pageable pageable, Model model){
        log.debug("=========userListAjax============");
        Page<Notice> noticeList = noticeService.getNoticeList(pageable);

        return noticeList;
    }

    @PostMapping("/noticeDelete")
    public ResultJsonPagingDto noticeDelete(@RequestParam(value = "deleteList[]") Integer[] deleteList){
        ResultJsonPagingDto dto = new ResultJsonPagingDto();
        try{
            log.debug("@@@@@@"+deleteList.length);
            log.debug("@@@@@@"+deleteList[0]);
            log.debug("@@@@@@"+deleteList[1]);
            noticeService.delete(deleteList);

            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deleteok"));

        }catch(Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
            dto.setSuccess(true);
            dto.setMessage(messageSourceAccessor.getMessage("deletefail"));

        }

        return dto;
    }
}
