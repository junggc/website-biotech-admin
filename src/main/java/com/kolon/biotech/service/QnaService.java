package com.kolon.biotech.service;

import com.kolon.biotech.domain.mailinfo.Mailinfo;
import com.kolon.biotech.domain.mailinfo.MailinfoRepository;
import com.kolon.biotech.domain.prbinfo.Prbinfo;
import com.kolon.biotech.domain.qna.Qna;
import com.kolon.biotech.domain.qna.QnaRepository;
import com.kolon.biotech.util.MailUtil;
import com.kolon.biotech.web.dto.MailDto;
import com.kolon.biotech.web.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private MailinfoRepository mailinfoRepository;

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;

    @Value("${server.profile}")
    private String serverProfile;

    @Autowired
    private MailUtil mailUtil;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Qna getInfo(Integer id){
        Qna obj;
        if(id!=null && id != 0){
            Optional<Qna> wapper = qnaRepository.findById(id);
            obj = wapper.get();
        }else{
            obj = new Qna();
        }

        return obj;
    }

    @Transactional(rollbackFor = Exception.class)
    public Page<Qna> getList(SearchDto searchDto, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startDate = LocalDateTime.parse(searchDto.getSearchStartDate().replaceAll("-","")+"000000",formatter);
        LocalDateTime endDate = LocalDateTime.parse(searchDto.getSearchEndDate().replaceAll("-","")+"235959",formatter);
        Page<Qna> list = null;

        if(searchDto.getSearchAnsStat() != null && "".equals(searchDto.getSearchAnsStat())){
            if(searchDto.getSearchText() != null && !"".equals(searchDto.getSearchText())){
                list = qnaRepository.findAllByRegDtimeBetweenAndUserNameLikeOrUserContentsLike("N",startDate,endDate,searchDto.getSearchText(),pageable);
            }else{
                list = qnaRepository.findAllByRegDtimeBetweenOrderByRegDtimeDesc("N",startDate, endDate,pageable);
            }
        }else{
            if(searchDto.getSearchText() != null && !"".equals(searchDto.getSearchText())){
                if("Y".equals(searchDto.getSearchAnsStat())){
                    list = qnaRepository.findAllByAnsStateYAndRegDtimeBetweenAndUserNameLikeOrUserContentsLike("N",startDate,endDate,searchDto.getSearchText(),pageable);
                }else{
                    list = qnaRepository.findAllByAnsStateNAndRegDtimeBetweenAndUserNameLikeOrUserContentsLike("N",startDate,endDate,searchDto.getSearchText(),pageable);
                }
            }else{
                if("Y".equals(searchDto.getSearchAnsStat())){
                    list = qnaRepository.findAllByAnsStateYAndRegDtimeBetweenOrderByRegDtimeDesc("N",startDate, endDate,pageable);
                }else{
                    list = qnaRepository.findAllByAnsStateNAndRegDtimeBetweenOrderByRegDtimeDesc("N",startDate, endDate,pageable);
                }

            }
        }


        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public Qna setWriteStroe(Qna obj, MultipartFile[] files, List<Integer> deleteFileList) throws Exception {
        log.debug("===================setWriteStroeNotice============================");
        log.debug("########"+obj.getId()+"#########");

        Qna _qna = qnaRepository.findById(obj.getId()).get();

//        obj.setAnswerDate(LocalDateTime.now());
//        Qna r_obj = qnaRepository.save(obj);

        _qna.setAnswerDate(LocalDateTime.now());
        _qna.setAnswerTitle(obj.getAnswerTitle());
        _qna.setAnswerContents(obj.getAnswerContents());

        //메일보내기
        Mailinfo mailinfo = mailinfoRepository.findByJob("REP".toUpperCase());

        MailDto mailDto = new MailDto();
        mailDto.setFromAddress(mailinfo.getAddress());
        mailDto.setToAddress(_qna.getUserEmail());
        mailDto.setUserName(_qna.getUserName());

        mailDto.setServerProfile(serverProfile);
        mailDto.setTitle("문의드립니다.");
        mailDto.setMessage(_qna.getUserContents());
        mailDto.setRegDtime(_qna.getRegDtime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //mailDto.setRegDtime(_qna.getUpdDtime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        mailDto.setSubject(mailinfo.getSubject());
        mailDto.setAnswerTitle(_qna.getAnswerTitle());
        mailDto.setAnswerContents(_qna.getAnswerContents());
        mailDto.setAnswerDate(_qna.getAnswerDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        String nlString = System.getProperty("line.separator").toString();
        mailDto.setNlString(nlString);

        mailUtil.sendMailInFile(mailDto);

        return _qna;

    }

    @Transactional
    public void delete(List<Integer> deleteList){

        //파일 삭제
        if(deleteList != null && !deleteList.isEmpty()){
            for(Integer id : deleteList){
                Qna qna = qnaRepository.findById(id).get();
                qna.setDelYn("Y");
            }

        }

    }


}
