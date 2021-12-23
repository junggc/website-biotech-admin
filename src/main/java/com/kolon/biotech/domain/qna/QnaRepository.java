package com.kolon.biotech.domain.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface QnaRepository extends JpaRepository<Qna, Integer> {

    Page<Qna> findAllByRegDtimeBetweenOrderByRegDtimeDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable paging);

    @Query("select e from Qna e where e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByRegDtimeBetweenAndUserNameLikeOrUserContentsLike(LocalDateTime startDate, LocalDateTime endDate, String searchWord, Pageable paging);

    @Query("select e from Qna e where e.answerDate is not null and e.regDtime between :startDate and :endDate order by e.regDtime desc")
    Page<Qna> findAllByAnsStateYAndRegDtimeBetweenOrderByRegDtimeDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable paging);

    @Query("select e from Qna e where e.answerDate is null and e.regDtime between :startDate and :endDate order by e.regDtime desc")
    Page<Qna> findAllByAnsStateNAndRegDtimeBetweenOrderByRegDtimeDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable paging);

    @Query("select e from Qna e where e.answerDate is not null and e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByAnsStateYAndRegDtimeBetweenAndUserNameLikeOrUserContentsLike(LocalDateTime startDate, LocalDateTime endDate, String searchWord, Pageable paging);

    @Query("select e from Qna e where e.answerDate is null and e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByAnsStateNAndRegDtimeBetweenAndUserNameLikeOrUserContentsLike(LocalDateTime startDate, LocalDateTime endDate, String searchWord, Pageable paging);
}
