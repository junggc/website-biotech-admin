package com.kolon.biotech.domain.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Integer> {

    public Page<Notice> findAllByTitleGreaterThan(String title, Pageable paging);

    public void deleteAllByIdIn(Integer[] list);
}
