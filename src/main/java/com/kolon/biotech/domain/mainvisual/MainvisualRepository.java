package com.kolon.biotech.domain.mainvisual;

import com.kolon.biotech.domain.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainvisualRepository extends JpaRepository<Mainvisual, Integer> {


    @Query("select max(e.orderSeq) as orderSeq from Mainvisual e")
    public int findMaxOrderSeq();

    public Mainvisual findByOrderSeq(int orderSeq);

    @Query("select min(e.orderSeq) as orderSeq from Mainvisual e")
    public int findMinOrderSeq();

    Page<Mainvisual> findAllByOrderByOrderSeqDesc(Pageable pageable);

    Page<Mainvisual> findAllByDispYnOrderByOrderSeqDesc(String dispYn, Pageable pageable);

    Page<Mainvisual> findAllByLangKoYnOrderByOrderSeqDesc(String langKorYn, Pageable pageable);

    Page<Mainvisual> findAllByDispYnAndLangKoYnOrderByOrderSeqDesc(String dispYn,String langKorYn, Pageable pageable);
}
