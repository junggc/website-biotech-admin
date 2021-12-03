package com.kolon.biotech.domain;

import com.kolon.biotech.domain.user.Member;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedBy
    @Column(name="REG_ID", nullable = true, updatable = false)
    private String regId;

    @LastModifiedBy
    @Column(name="UPD_ID", nullable = true, updatable = true)
    private String updId;

    @CreatedDate
    @Column(name="REG_DTIME", nullable = false, updatable = false)
    private LocalDateTime regDtime;

    @LastModifiedDate
    @Column(name="UPD_DTIME", nullable = false, updatable = true)
    private LocalDateTime updDtime;

}
