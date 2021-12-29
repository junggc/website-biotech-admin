package com.kolon.biotech.domain.mainpop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainPopRepository extends JpaRepository<Mainpop, Integer> {

    Page<Mainpop> findAllByOrderByIdDesc(Pageable pageable);

    Page<Mainpop> findAllByDispYnOrderByIdDesc(String dispYn, Pageable pageable);

    Page<Mainpop> findAllByLangKoYnOrderByIdDesc(String langKoYn, Pageable pageable);

    Page<Mainpop> findAllByDispYnAndLangKoYnOrderByIdDesc(String dispYn, String langKoYn, Pageable pageable);
}
