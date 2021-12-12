package com.kolon.biotech.service;

import com.kolon.biotech.domain.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;



}
