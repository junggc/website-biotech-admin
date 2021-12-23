package com.kolon.biotech.service;

import com.kolon.biotech.domain.mainpop.MainPopRepository;
import com.kolon.biotech.domain.mainpop.Mainpop;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MainpopService {

    @Autowired
    private MainPopRepository mainPopRepository;

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;


    public Mainpop getInfo(Integer id){
        Mainpop mainpop;
        if(id!=null && id != 0){
            Optional<Mainpop> mainvisualWapper = mainPopRepository.findById(id);
            mainpop = mainvisualWapper.get();
        }else{
            mainpop = new Mainpop();
        }

        return mainpop;
    }

    public Page<Mainpop> getList(Mainpop mainpop, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));

        Page<Mainpop> list = null;

        if(mainpop.getDispYn() != null && !"".equals(mainpop.getDispYn())){
            list = mainPopRepository.findAllByDispYnOrderByIdDesc(mainpop.getDispYn(), pageable);
        }else{
            list = mainPopRepository.findAllByOrderByIdDesc(pageable);
        }

        return list;
    }

    @Transactional
    public Mainpop setWriteStroe(Mainpop obj, MultipartFile file, String deleteFile) throws IOException {
        log.debug("===================setWriteStroeNotice============================");
        log.debug("########"+obj.getId()+"#########");

        Mainpop _mainpop = null;
        if(obj.getId() != null && obj.getId() > 0){
            _mainpop = mainPopRepository.findById(obj.getId()).get();
        }

        //파일 삭제
        if(deleteFile != null && !"".equals(deleteFile)){
            File f = new File(deleteFile);
            f.delete();
        }

        if(file != null && !file.isEmpty()){

            if(_mainpop != null && (_mainpop.getPopImgRealPath() != null && !"".equals(_mainpop.getPopImgRealPath()))){
                File f = new File(_mainpop.getPopImgRealPath());
                f.delete();
            }

            log.debug("######"+file.getOriginalFilename()+"######");
            String oriFileName = file.getOriginalFilename();
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date()) + "." + FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
            String path = "/mainpop";
            String filePath = targetRootLocation + path;

            Path fileLocation = Paths.get(filePath);
            if (!Files.isDirectory(fileLocation)) {
                Files.createDirectories(fileLocation);
            }

            Files.copy(file.getInputStream(), fileLocation.resolve(newFilename));

            obj.setPopImgName(oriFileName);
            obj.setPopImgPath(uriPath+"/"+path+"/"+newFilename);
            obj.setPopImgRealPath(filePath + "/" + newFilename);
            obj.setPopImgExt(FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase());
            obj.setPopImgLength(String.valueOf(file.getSize()));
        }else{
            if(_mainpop != null){
                obj.setPopImgName(_mainpop.getPopImgName());
                obj.setPopImgPath(_mainpop.getPopImgPath());
                obj.setPopImgRealPath(_mainpop.getPopImgRealPath());
                obj.setPopImgExt(_mainpop.getPopImgExt());
                obj.setPopImgLength(_mainpop.getPopImgLength());
            }
        }

        Mainpop r_notice = mainPopRepository.save(obj);

        return r_notice;

    }

    @Transactional
    public void delete(List<Integer> deleteList){
        //파일 삭제 및 글삭제
        if(deleteList != null && !deleteList.isEmpty()){
            for(Integer id : deleteList){

                //실제파일 삭제
                Mainpop mainpop = mainPopRepository.findById(id).get();
                if(mainpop.getPopImgRealPath() != null && !"".equals(mainpop.getPopImgRealPath())){
                    File f = new File(mainpop.getPopImgRealPath());
                    f.delete();
                }

                mainPopRepository.deleteById(id);
            }
        }
    }



}
