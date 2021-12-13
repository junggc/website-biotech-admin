package com.kolon.biotech.domain.mainvisual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainvisualRepository extends JpaRepository<Mainvisual, Integer> {


    @Query("select max(e.orderSeq)+1 as orderSeq from Mainvisual e")
    public int findMaxOrderSeq();

    public Mainvisual findByOrderSeq(int orderSeq);
}
