package com.kolon.biotech.service;

import com.kolon.biotech.domain.mainpop.Mainpop;
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

        Mainvisual _mainvisual = null;
        if(mainvisual.getId() != null && mainvisual.getId() > 0){
            _mainvisual = mainvisualRepository.findById(mainvisual.getId()).get();
        }
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

            if(_mainvisual != null){
                File f = new File(_mainvisual.getPcImgRealPath());
                f.delete();
            }

            String oriFileName = miFile.getOriginalFilename();
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date()) + "." + FilenameUtils.getExtension(miFile.getOriginalFilename()).toLowerCase();

            Files.copy(miFile.getInputStream(), fileLocation.resolve(newFilename));

            _uriPath = uriPath+path+"/"+newFilename;
            realFilePath = filePath + "/" + newFilename;

            mainvisual.setPcImgName(oriFileName);
            mainvisual.setPcImgPath(_uriPath);
            mainvisual.setPcImgRealPath(realFilePath);
            mainvisual.setPcImgExt(FilenameUtils.getExtension(miFile.getOriginalFilename()).toLowerCase());
            mainvisual.setPcImgLength(String.valueOf(miFile.getSize()));

        }else{
            mainvisual.setPcImgName(_mainvisual.getPcImgName());
            mainvisual.setPcImgPath(_mainvisual.getPcImgPath());
            mainvisual.setPcImgRealPath(_mainvisual.getPcImgRealPath());
            mainvisual.setPcImgExt(_mainvisual.getPcImgExt());
            mainvisual.setPcImgLength(_mainvisual.getPcImgLength());
        }

        if(siFile != null && !siFile.isEmpty()){
            if(_mainvisual != null){
                File f = new File(_mainvisual.getMoImgRealPath());
                f.delete();
            }

            String oriFileName = siFile.getOriginalFilename();
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date()) + "." + FilenameUtils.getExtension(siFile.getOriginalFilename()).toLowerCase();;

            Files.copy(siFile.getInputStream(), fileLocation.resolve(newFilename));

            _uriPath = uriPath+path+"/"+newFilename;
            realFilePath = filePath + "/" + newFilename;

            mainvisual.setMoImgName(oriFileName);
            mainvisual.setMoImgPath(_uriPath);
            mainvisual.setMoImgRealPath(realFilePath);
            mainvisual.setMoImgExt(FilenameUtils.getExtension(siFile.getOriginalFilename()).toLowerCase());
            mainvisual.setMoImgLength(String.valueOf(siFile.getSize()));
        }else{
            mainvisual.setMoImgName(_mainvisual.getMoImgName());
            mainvisual.setMoImgPath(_mainvisual.getMoImgPath());
            mainvisual.setMoImgRealPath(_mainvisual.getMoImgRealPath());
            mainvisual.setMoImgExt(_mainvisual.getMoImgExt());
            mainvisual.setMoImgLength(_mainvisual.getMoImgLength());
        }

        if(mvFile != null && !mvFile.isEmpty()){
            if(_mainvisual != null){
                File f = new File(_mainvisual.getVideosRealPath());
                f.delete();
            }

            String oriFileName = mvFile.getOriginalFilename();
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date()) + "." + FilenameUtils.getExtension(mvFile.getOriginalFilename()).toLowerCase();;

            Files.copy(mvFile.getInputStream(), fileLocation.resolve(newFilename));

            _uriPath = uriPath+"/"+path+"/"+newFilename;
            realFilePath = filePath + "/" + newFilename;

            mainvisual.setVideosName(oriFileName);
            mainvisual.setVideosPath(_uriPath);
            mainvisual.setVideosRealPath(realFilePath);
            mainvisual.setVideosExt(FilenameUtils.getExtension(mvFile.getOriginalFilename()).toLowerCase());
            mainvisual.setVideosLength(String.valueOf(mvFile.getSize()));
        }else{
            mainvisual.setVideosName(_mainvisual.getVideosName());
            mainvisual.setVideosPath(_mainvisual.getVideosPath());
            mainvisual.setVideosRealPath(_mainvisual.getVideosRealPath());
            mainvisual.setVideosExt(_mainvisual.getVideosExt());
            mainvisual.setVideosLength(_mainvisual.getVideosLength());
        }

        Mainvisual r_mainvidual = mainvisualRepository.save(mainvisual);

        return r_mainvidual;

    }

    @Transactional
    public void delete(List<Integer> deleteList){
        if(deleteList != null && !deleteList.isEmpty()){
            for(Integer id : deleteList){

                //실제파일 삭제
                Mainvisual mainvisual = mainvisualRepository.findById(id).get();

                if(mainvisual.getPcImgRealPath() != null && !"".equals(mainvisual.getPcImgRealPath())){
                    File f = new File(mainvisual.getPcImgRealPath());
                    f.delete();
                }

                if(mainvisual.getMoImgRealPath() != null && !"".equals(mainvisual.getMoImgRealPath())){
                    File f = new File(mainvisual.getMoImgRealPath());
                    f.delete();
                }

                if(mainvisual.getVideosRealPath() != null && !"".equals(mainvisual.getVideosRealPath())){
                    File f = new File(mainvisual.getVideosRealPath());
                    f.delete();
                }


                mainvisualRepository.deleteById(id);
            }
        }
    }

}
