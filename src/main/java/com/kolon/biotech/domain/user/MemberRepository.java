package com.kolon.biotech.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer>, JpaSpecificationExecutor<Member> {

    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByLoginIdAndUseYn(String loginId, String useYn);
    Page<Member> findAll(Specification<Member> spec, Pageable pageable);

    @Query("update Member e set e.blocked = 'false', e.failCount=0 where e.id=:ida")
    Member updateLoginReset(Integer ida);
}
