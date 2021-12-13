package com.kolon.biotech.domain.mailinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MAILINFO")
@DynamicInsert
@DynamicUpdate
public class Mailinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAILINFO_ID", nullable = false)
    private Integer id;

    @Column(name = "JOB")
    private String job;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "subject")
    private String subject;
}
