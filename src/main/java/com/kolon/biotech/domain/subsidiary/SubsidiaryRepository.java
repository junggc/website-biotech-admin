package com.kolon.biotech.domain.subsidiary;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubsidiaryRepository extends JpaRepository<Subsidiary, Integer> {

    Optional<Subsidiary> findByOrderSeq(String seq);
}
