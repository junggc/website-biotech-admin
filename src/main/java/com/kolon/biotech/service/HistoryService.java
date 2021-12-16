package com.kolon.biotech.service;

import com.kolon.biotech.domain.history.History;
import com.kolon.biotech.domain.history.HistoryRepository;
import com.kolon.biotech.domain.prbinfo.Prbinfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

}
