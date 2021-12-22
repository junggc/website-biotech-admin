package com.kolon.biotech.domain.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRepository  extends JpaRepository<History, Integer> {

    Page<History> findAllByJobFlagAndRequestDateBetweenOrderByRegDtimeDesc(String jobFlag, LocalDateTime startDate, LocalDateTime endDate, Pageable paging);

    @Query("select e from History e where e.jobFlag=:jobFlag and e.requestDate between :startDate and :endDate and e.jobContent like %:searchWord%  order by e.regDtime desc")
    Page<History> qfindAllByJobFlagAndRequestDateBetweenAndJobContentLikeOrderByRegDtimeDesc(String jobFlag, LocalDateTime startDate, LocalDateTime endDate, String searchWord, Pageable paging);

    @Query("select e from History e where e.jobFlag=:jobFlag and e.requestDate between :startDate and :endDate and (e.userId like %:searchWord% or e.userName like %:searchWord%) order by e.regDtime desc")
    Page<History> qfindAllByJobFlagAndRequestDateBetweenAndUserIdLikeOrUserNameLikeOrderByRegDtimeDesc(String jobFlag, LocalDateTime startDate, LocalDateTime endDate, String searchWord, Pageable paging);

    List<History> findAllByJobFlagAndRequestDateBetweenOrderByRegDtimeDesc(String jobFlag, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select e from History e where e.jobFlag=:jobFlag and e.requestDate between :startDate and :endDate and e.jobContent like %:searchWord%  order by e.regDtime desc")
    List<History> qpfindAllByJobFlagAndRequestDateBetweenAndJobContentLikeOrderByRegDtimeDesc(String jobFlag, LocalDateTime startDate, LocalDateTime endDate, String searchWord);

    @Query("select e from History e where e.jobFlag=:jobFlag and e.requestDate between :startDate and :endDate and (e.userId like %:searchWord% or e.userName like %:searchWord%) order by e.regDtime desc")
    List<History> qpfindAllByJobFlagAndRequestDateBetweenAndUserIdLikeOrUserNameLikeOrderByRegDtimeDesc(String jobFlag, LocalDateTime startDate, LocalDateTime endDate, String searchWord);
}
