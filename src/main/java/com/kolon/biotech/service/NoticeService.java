package com.kolon.biotech.service;

import com.kolon.biotech.domain.notice.Notice;
import com.kolon.biotech.domain.notice.NoticeRepository;
import com.kolon.biotech.domain.notice.Noticefile;
import com.kolon.biotech.domain.notice.NoticefileRepository;
import com.kolon.biotech.domain.user.Member;
import com.zaxxer.hikari.util.FastList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticefileRepository noticefileRepository;

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;

    @Transactional
    public Notice noticeSaveAndUpdate(Notice notice){
        return noticeRepository.save(notice);
    }

    public Notice getNotice(Integer id){
        Notice notice;
        if(id != null && id != 0){
            Optional<Notice> noticeWrapper = noticeRepository.findById(id);
            notice = noticeWrapper.get();
        }else{
            notice = new Notice();
        }

        return notice;
    }

    public Page<Notice> getNoticeList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));
        return noticeRepository.findAll(pageable);
    }

    @Transactional
    public Notice setWriteStroeNotice(Notice notice, MultipartFile[] files, List<Integer> deleteFileList) throws IOException {
        log.debug("===================setWriteStroeNotice============================");
        log.debug("########"+notice.getId()+"#########");
        Notice r_notice = noticeRepository.save(notice);

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

                Noticefile noticefile = Noticefile.builder().noticeId(r_notice.getId()).fileName(oriFileName)
                        .filePath(filePath+"/"+newFilename)
                        .uriPath(uriPath+"/"+path+"/"+newFilename)
                        .fileLength(Long.toString(file.getSize()))
                        .fileExt(FilenameUtils.getExtension(oriFileName)).build();

                noticefileRepository.save(noticefile);


            }
        }

        //파일 삭제
        if(deleteFileList != null && !deleteFileList.isEmpty()){
            for(Integer id : deleteFileList){

                //실제 파일 삭제
                Noticefile nfile = noticefileRepository.findById(id).get();
                File f = new File(nfile.getFilePath());
                f.delete();

                noticefileRepository.deleteById(id);
            }

        }


        return r_notice;

    }

    @Transactional
    public void delete(Integer[] deleteList){

        //파일 삭제
        if(deleteList != null && deleteList.length > 0){
            //실제 파일 삭제
            List<Noticefile> nfile = noticefileRepository.findAllByNoticeIdIn(deleteList);
            for(Noticefile nf : nfile){
                File f = new File(nf.getFilePath());
                if(f!=null && f.isFile()){
                    f.delete();
                }
            }

            noticefileRepository.deleteAllByIdIn(deleteList);
            noticeRepository.deleteAllByIdIn(deleteList);

        }

    }

}
