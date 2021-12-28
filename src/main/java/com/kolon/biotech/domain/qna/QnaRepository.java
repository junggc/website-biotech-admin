package com.kolon.biotech.domain.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface QnaRepository extends JpaRepository<Qna, Integer> {

    @Query("select e from Qna e where e.delYn = :delYn and e.regDtime between :startDate and :endDate order by e.regDtime desc")
    Page<Qna> findAllByRegDtimeBetweenOrderByRegDtimeDesc(@Param("delYn") String delYn, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByRegDtimeBetweenAndUserNameLikeOrUserContentsLike(@Param("delYn")String delYn, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("searchWord")String searchWord, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.answerDate is not null and e.regDtime between :startDate and :endDate order by e.regDtime desc")
    Page<Qna> findAllByAnsStateYAndRegDtimeBetweenOrderByRegDtimeDesc(@Param("delYn")String delYn, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.answerDate is null and e.regDtime between :startDate and :endDate order by e.regDtime desc")
    Page<Qna> findAllByAnsStateNAndRegDtimeBetweenOrderByRegDtimeDesc(@Param("delYn")String delYn, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.answerDate is not null and e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByAnsStateYAndRegDtimeBetweenAndUserNameLikeOrUserContentsLike(@Param("delYn")String delYn, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("searchWord")String searchWord, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.answerDate is null and e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByAnsStateNAndRegDtimeBetweenAndUserNameLikeOrUserContentsLike(@Param("delYn")String delYn, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("searchWord")String searchWord, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.qnaCate = :qnaCate and e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByRegDtimeBetweenAndUserNameLikeOrUserContentsLike(@Param("delYn")String delYn, @Param("qnaCate")String qnaCate, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("searchWord")String searchWord, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.qnaCate = :qnaCate and e.regDtime between :startDate and :endDate order by e.regDtime desc")
    Page<Qna> findAllByRegDtimeBetweenOrderByRegDtimeDesc(@Param("delYn") String delYn, @Param("qnaCate")String qnaCate, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.qnaCate = :qnaCate and e.answerDate is not null and e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByAnsStateYAndRegDtimeBetweenAndUserNameLikeOrUserContentsLike(@Param("delYn")String delYn, @Param("qnaCate")String qnaCate, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("searchWord")String searchWord, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.qnaCate = :qnaCate and e.answerDate is null and e.regDtime between :startDate and :endDate and (e.userName like %:searchWord% or e.userContents like %:searchWord%) order by e.regDtime desc")
    Page<Qna> findAllByAnsStateNAndRegDtimeBetweenAndUserNameLikeOrUserContentsLike(@Param("delYn")String delYn, @Param("qnaCate")String qnaCate, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("searchWord")String searchWord, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.qnaCate = :qnaCate and e.answerDate is not null and e.regDtime between :startDate and :endDate order by e.regDtime desc")
    Page<Qna> findAllByAnsStateYAndRegDtimeBetweenOrderByRegDtimeDesc(@Param("delYn")String delYn, @Param("qnaCate")String qnaCate, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, Pageable paging);

    @Query("select e from Qna e where e.delYn = :delYn and e.qnaCate = :qnaCate and e.answerDate is null and e.regDtime between :startDate and :endDate order by e.regDtime desc")
    Page<Qna> findAllByAnsStateNAndRegDtimeBetweenOrderByRegDtimeDesc(@Param("delYn")String delYn, @Param("qnaCate")String qnaCate, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, Pageable paging);
}
