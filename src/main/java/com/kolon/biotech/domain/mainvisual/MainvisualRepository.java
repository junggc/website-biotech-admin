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

    public Mainvisual findTop1ByOrderSeqBeforeOrderByOrderSeqDesc(int orderSeq);

    public Mainvisual findTop1ByOrderSeqBeforeAndDispYnOrderByOrderSeqDesc(int orderSeq ,String dispYn);

    public Mainvisual findTop1ByOrderSeqBeforeAndLangKoYnOrderByOrderSeqDesc(int orderSeq, String langKoYn);

    public Mainvisual findTop1ByOrderSeqBeforeAndDispYnAndLangKoYnOrderByOrderSeqDesc(int orderSeq, String dispYn, String langKoYn);

    public Mainvisual findTop1ByOrderSeqAfterOrderByOrderSeqAsc(int orderSeq);

    public Mainvisual findTop1ByOrderSeqAfterAndDispYnOrderByOrderSeqAsc(int orderSeq, String dispYn);

    public Mainvisual findTop1ByOrderSeqAfterAndLangKoYnOrderByOrderSeqAsc(int orderSeq, String langKoYn);

    public Mainvisual findTop1ByOrderSeqAfterAndDispYnAndLangKoYnOrderByOrderSeqAsc(int orderSeq, String dispYn, String langKoYn);

    @Query("select min(e.orderSeq) as orderSeq from Mainvisual e")
    public int findMinOrderSeq();

    Page<Mainvisual> findAllByOrderByOrderSeqDesc(Pageable pageable);

    Page<Mainvisual> findAllByDispYnOrderByOrderSeqDesc(String dispYn, Pageable pageable);

    Page<Mainvisual> findAllByLangKoYnOrderByOrderSeqDesc(String langKorYn, Pageable pageable);

    Page<Mainvisual> findAllByDispYnAndLangKoYnOrderByOrderSeqDesc(String dispYn,String langKorYn, Pageable pageable);
}
