package com.kolon.biotech.domain.user;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
//https://groti.tistory.com/49
public class MemberSpecification {
    public static Specification<Member> equalId(Integer Id) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 1) equal
                return criteriaBuilder.equal(root.get("Id"), Id);
            }
        };
    }

    public static Specification<Member> likeContents(String contents) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("contents"), "%" + contents + "%");
            }
        };
    }

    public static Specification<Member> betweenCreatedDatetime(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 3) between
                return criteriaBuilder.between(root.get("createdDatetime"), startDatetime, endDatetime);
            }
        };
    }
}
