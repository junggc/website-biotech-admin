package com.kolon.biotech.domain.subsidiary;

import com.kolon.biotech.domain.mainvisual.Mainvisual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubsidiaryRepository extends JpaRepository<Subsidiary, Integer> {

    Optional<Subsidiary> findByOrderSeq(String seq);

    Page<Subsidiary> findAllByOrderByOrderSeqAsc(Pageable pageable);

    public Subsidiary findTop1ByOrderSeqBeforeOrderByOrderSeqDesc(int orderSeq);

    public Subsidiary findTop1ByOrderSeqAfterOrderByOrderSeqAsc(int orderSeq);

    @Query("select max(e.orderSeq) as orderSeq from Subsidiary e")
    public int findMaxOrderSeq();

    @Query("select min(e.orderSeq) as orderSeq from Subsidiary e")
    public int findMinOrderSeq();
}
