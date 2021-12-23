package com.kolon.biotech.service;

import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.domain.history.HistoryRepository;
import com.kolon.biotech.domain.prbinfo.Prbinfo;
import com.kolon.biotech.web.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;


    @Transactional
    public History setWriteStroe(History obj) throws IOException {
        log.debug("===================setWriteStroeNotice============================");
        log.debug("########"+obj.getId()+"#########");

        History r_obj = historyRepository.save(obj);
        return r_obj;

    }

    @Transactional(readOnly = true)
    public Page<History> getList(SearchDto searchDto, Pageable pageable) throws Exception{
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startDate = LocalDateTime.parse(searchDto.getSearchStartDate().replaceAll("-","")+"000000",formatter);
        LocalDateTime endDate = LocalDateTime.parse(searchDto.getSearchEndDate().replaceAll("-","")+"235959",formatter);

        Page<History> list = null;

        if(searchDto.getSearchText() != null && !"".equals(searchDto.getSearchText())){

            if("J".equals(searchDto.getJobFlag())){
                list = historyRepository.qfindAllByJobFlagAndRequestDateBetweenAndJobContentLikeOrderByRegDtimeDesc(searchDto.getJobFlag(), startDate, endDate, searchDto.getSearchText(), pageable);
            }else{
                list = historyRepository.qfindAllByJobFlagAndRequestDateBetweenAndUserIdLikeOrUserNameLikeOrderByRegDtimeDesc(searchDto.getJobFlag(), startDate, endDate, searchDto.getSearchText(), pageable);
            }
        }else{
            if("J".equals(searchDto.getJobFlag())){
                list = historyRepository.findAllByRequestDateBetweenOrderByRegDtimeDesc(startDate, endDate, pageable);
            }else{
                list = historyRepository.findAllByJobFlagAndRequestDateBetweenOrderByRegDtimeDesc(searchDto.getJobFlag(), startDate, endDate, pageable);
            }

        }

        return list;
    }

    @Transactional(readOnly = true)
    public List<History> getList(SearchDto searchDto) throws Exception{

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startDate = LocalDateTime.parse(searchDto.getSearchStartDate().replaceAll("-","")+"000000",formatter);
        LocalDateTime endDate = LocalDateTime.parse(searchDto.getSearchEndDate().replaceAll("-","")+"235959",formatter);

        List<History> list = null;

        if(searchDto.getSearchText() != null && !"".equals(searchDto.getSearchText())){
            if("J".equals(searchDto.getJobFlag())){
                list = historyRepository.qpfindAllByJobFlagAndRequestDateBetweenAndJobContentLikeOrderByRegDtimeDesc(searchDto.getJobFlag(), startDate, endDate, searchDto.getSearchText());
            }else{
                list = historyRepository.qpfindAllByJobFlagAndRequestDateBetweenAndUserIdLikeOrUserNameLikeOrderByRegDtimeDesc(searchDto.getJobFlag(), startDate, endDate, searchDto.getSearchText());
            }
        }else{
            if("J".equals(searchDto.getJobFlag())){
                list = historyRepository.findAllByRequestDateBetweenOrderByRegDtimeDesc(startDate,endDate);
            }else{
                list = historyRepository.findAllByJobFlagAndRequestDateBetweenOrderByRegDtimeDesc(searchDto.getJobFlag(), startDate, endDate);
            }
        }

        return list;
    }

}
