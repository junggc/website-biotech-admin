package com.kolon.biotech.domain.history;

import com.kolon.biotech.domain.BaseTimeEntity;
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
@Table(name = "HISTORY")
@DynamicInsert
@DynamicUpdate
public class History extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HISTORY_ID", nullable = false)
    private Integer id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "JOB_CONTENT")
    private String jobContent;

    @Column(name = "JOB_URL")
    private String jobUrl;

    @Column(name = "REQUEST_IP")
    private String requestIp;

    @Column(name = "REQUEST_DATE")
    private String requestDate;

}
