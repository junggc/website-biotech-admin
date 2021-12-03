package com.kolon.biotech.domain.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticefileRepository extends JpaRepository<Noticefile, Integer> {

    public List<Noticefile> findAllByNoticeId(Integer noticeId);

    public void deleteByNoticeId(Integer noticeId);

}
