package com.kolon.biotech.service;

import com.kolon.biotech.domain.prbinfo.Prbinfo;
import com.kolon.biotech.domain.qna.Qna;
import com.kolon.biotech.domain.qna.QnaRepository;
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
public class QnaService {

    private final QnaRepository qnaRepository;

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;

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

    public Page<Qna> getList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));
        return qnaRepository.findAll(pageable);
    }

    @Transactional
    public Qna setWriteStroe(Qna obj, MultipartFile[] files, List<Integer> deleteFileList) throws IOException {
        log.debug("===================setWriteStroeNotice============================");
        log.debug("########"+obj.getId()+"#########");
        Qna r_obj = qnaRepository.save(obj);

        if(files != null && files.length > 0){

            int i=0;
            for(MultipartFile file : files){
                log.debug("######"+file.getOriginalFilename()+"######");
                String oriFileName = file.getOriginalFilename();
                String newFilename = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date()) + "." + FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();;
                String path = "/notice";
                String filePath = targetRootLocation + path;

                Path fileLocation = Paths.get(filePath);
                if (!Files.isDirectory(fileLocation)) {
                    Files.createDirectories(fileLocation);
                }

                Files.copy(file.getInputStream(), fileLocation.resolve(newFilename));

//                File f = new File(filePath+"/"+newFilename);
//
//                file.transferTo(f);




            }
        }

        //파일 삭제
        if(deleteFileList != null && !deleteFileList.isEmpty()){
            for(Integer id : deleteFileList){

                //실제 파일 삭제
                Qna nfile = qnaRepository.findById(id).get();
//                File f = new File(nfile.getFilePath());
//                f.delete();

                qnaRepository.deleteById(id);
            }

        }


        return r_obj;

    }

    @Transactional
    public void delete(List<Integer> deleteFileList){

        //파일 삭제
        if(deleteFileList != null && !deleteFileList.isEmpty()){
            for(Integer id : deleteFileList){

                //실제 파일 삭제
                Qna nfile = qnaRepository.findById(id).get();
//                File f = new File(nfile.getFilePath());
//                f.delete();

                qnaRepository.deleteById(id);
            }

        }

    }


}
