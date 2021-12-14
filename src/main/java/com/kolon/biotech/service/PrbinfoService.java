package com.kolon.biotech.service;

import com.kolon.biotech.domain.mainpop.Mainpop;
import com.kolon.biotech.domain.prbinfo.Prbinfo;
import com.kolon.biotech.domain.prbinfo.PrbinfoRepository;
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
@Service
public class PrbinfoService {

    @Autowired
    private PrbinfoRepository prbinfoRepository;

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;

    public Prbinfo getInfo(Integer id){
        Prbinfo obj;
        if(id!=null && id != 0){
            Optional<Prbinfo> wapper = prbinfoRepository.findById(id);
            obj = wapper.get();
        }else{
            obj = new Prbinfo();
        }

        return obj;
    }

    public Page<Prbinfo> getList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));
        return prbinfoRepository.findAll(pageable);
    }

    @Transactional
    public Prbinfo setWriteStroe(Prbinfo obj, MultipartFile[] files, List<Integer> deleteFileList) throws IOException {
        log.debug("===================setWriteStroeNotice============================");
        log.debug("########"+obj.getId()+"#########");

        Prbinfo r_obj = prbinfoRepository.save(obj);
        return r_obj;

    }

    @Transactional
    public void delete(List<Integer> deleteFileList){

        //파일 삭제
        if(deleteFileList != null && !deleteFileList.isEmpty()){
            for(Integer id : deleteFileList){

                //실제 파일 삭제
//                Prbinfo nfile = prbinfoRepository.findById(id).get();
//                File f = new File(nfile.getFilePath());
//                f.delete();

                prbinfoRepository.deleteById(id);
            }

        }

    }

}
