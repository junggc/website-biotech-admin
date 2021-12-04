package com.kolon.biotech.service;

import com.kolon.biotech.domain.mainvisual.Mainvisual;
import com.kolon.biotech.domain.mainvisual.MainvisualRepository;
import com.kolon.biotech.domain.notice.Notice;
import com.kolon.biotech.domain.notice.Noticefile;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequiredArgsConstructor
@Service
public class MainvisualService {

    private final MainvisualRepository mainvisualRepository;

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;

    public Mainvisual getMainvisual(Integer id){
        Mainvisual mainvisual;
        if(id!=null && id != 0){
            Optional<Mainvisual> mainvisualWapper = mainvisualRepository.findById(id);
            mainvisual = mainvisualWapper.get();
        }else{
            mainvisual = new Mainvisual();
        }

        return mainvisual;
    }

    public Page<Mainvisual> getMainvisualList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));
        return mainvisualRepository.findAll(pageable);
    }

    @Transactional
    public Mainvisual setWriteStroe(Mainvisual mainvisual
            , MultipartFile miFile
            ,MultipartFile siFile
            ,MultipartFile mvFile
            ,String deleteMiFile
            ,String deleteSiFile
            ,String deleteMvFile) throws IOException {
        log.debug("===================setWriteStroe============================");
        log.debug("########"+mainvisual.getId()+"#########");

        //파일삭제
        if(deleteMiFile != null && !"".equals(deleteMiFile)){
            File f = new File(deleteMiFile);
            f.delete();
        }

        //파일삭제
        if(deleteSiFile != null && !"".equals(deleteSiFile)){
            File f = new File(deleteSiFile);
            f.delete();
        }

        //파일삭제
        if(deleteMvFile != null && !"".equals(deleteMvFile)){
            File f = new File(deleteMvFile);
            f.delete();
        }


        //파일먼저 올리기
        String path = "/mainvisual";
        String filePath = targetRootLocation + path;
        Path fileLocation = Paths.get(filePath);
        if (!Files.isDirectory(fileLocation)) {
            Files.createDirectories(fileLocation);
        }

        String _uriPath = "";
        String realFilePath = "";

        if(miFile != null && !miFile.isEmpty()){
            String oriFileName = miFile.getOriginalFilename();
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date()) + "." + FilenameUtils.getExtension(miFile.getOriginalFilename()).toLowerCase();;

            Files.copy(miFile.getInputStream(), fileLocation.resolve(newFilename));

            _uriPath = uriPath+path+"/"+newFilename;
            realFilePath = filePath + "/" + newFilename;

            mainvisual.setPcImgName(oriFileName);
            mainvisual.setPcImgPath(_uriPath);
            mainvisual.setPcImgRealPath(realFilePath);

        }

        if(siFile != null && !siFile.isEmpty()){
            String oriFileName = siFile.getOriginalFilename();
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date()) + "." + FilenameUtils.getExtension(siFile.getOriginalFilename()).toLowerCase();;

            Files.copy(siFile.getInputStream(), fileLocation.resolve(newFilename));

            _uriPath = uriPath+path+"/"+newFilename;
            realFilePath = filePath + "/" + newFilename;

            mainvisual.setMoImgName(oriFileName);
            mainvisual.setMoImgPath(_uriPath);
            mainvisual.setMoImgRealPath(realFilePath);
        }

        if(mvFile != null && !mvFile.isEmpty()){
            String oriFileName = mvFile.getOriginalFilename();
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date()) + "." + FilenameUtils.getExtension(mvFile.getOriginalFilename()).toLowerCase();;

            Files.copy(mvFile.getInputStream(), fileLocation.resolve(newFilename));

            _uriPath = uriPath+"/"+path+"/"+newFilename;
            realFilePath = filePath + "/" + newFilename;

            mainvisual.setVideosName(oriFileName);
            mainvisual.setVideosPath(_uriPath);
            mainvisual.setVideosRealPath(realFilePath);
        }

        Mainvisual r_mainvidual = mainvisualRepository.save(mainvisual);

        return r_mainvidual;

    }

}
