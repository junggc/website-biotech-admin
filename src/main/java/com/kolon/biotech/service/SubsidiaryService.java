package com.kolon.biotech.service;

import com.kolon.biotech.domain.prbinfo.Prbinfo;
import com.kolon.biotech.domain.subsidiary.Subsidiary;
import com.kolon.biotech.domain.subsidiary.SubsidiaryRepository;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubsidiaryService {

    private final SubsidiaryRepository subsidiaryRepository;

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;

    public Subsidiary getInfo(Integer id){
        Subsidiary obj;
        if(id!=null && id != 0){
            Optional<Subsidiary> wapper = subsidiaryRepository.findById(id);
            obj = wapper.get();
        }else{
            obj = new Subsidiary();
        }

        return obj;
    }

    public Page<Subsidiary> getList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));
        return subsidiaryRepository.findAll(pageable);
    }

    @Transactional
    public Subsidiary setWriteStroe(Subsidiary obj) throws Exception {
        log.debug("===================setWriteStroeNotice============================");
        log.debug("########"+obj.getId()+"#########");

        Long totalCount = subsidiaryRepository.findAll().stream().count();
        int tMax = totalCount.intValue()+1;

        String codeValue = String.format("%03d",tMax);

        obj.setCode("KF"+codeValue);
        obj.setOrderSeq(String.valueOf(tMax));

        Subsidiary r_obj = subsidiaryRepository.save(obj);

        return r_obj;

    }

    @Transactional
    public void delete(List<Integer> deleteList)throws Exception{

        if(deleteList != null && !deleteList.isEmpty()){
            for(Integer id : deleteList){
                subsidiaryRepository.deleteById(id);
            }

        }

    }

    //전시순서 변경
    @Transactional
    public void updateOrderSeq(Integer id, String flag)throws Exception{

        Subsidiary subsidiary = subsidiaryRepository.findById(id).get();

        if("up".equals(flag)){
            String seq = subsidiary.getOrderSeq();
            int _seq = Integer.valueOf(seq);
            int t_seq = 0;
            if(_seq > 1){
                t_seq = _seq - 1;

                //위의 정보
                Subsidiary t_subsidiary = subsidiaryRepository.findByOrderSeq(String.valueOf(t_seq)).get();

                //optional이라 자동수정되는가?
                subsidiary.setOrderSeq(String.valueOf(t_seq));
                t_subsidiary.setOrderSeq(String.valueOf(_seq));

            }

        }else{
            String seq = subsidiary.getOrderSeq();
            int maxCount = subsidiaryRepository.findAll().size();
            int _seq = Integer.valueOf(seq);
            int t_seq = 0;
            if(_seq < maxCount){
                t_seq = _seq + 1;

                //위의 정보
                Subsidiary t_subsidiary = subsidiaryRepository.findByOrderSeq(String.valueOf(t_seq)).get();

                //optional이라 자동수정되는가?
                subsidiary.setOrderSeq(String.valueOf(t_seq));
                t_subsidiary.setOrderSeq(String.valueOf(_seq));

            }
        }
    }
}
