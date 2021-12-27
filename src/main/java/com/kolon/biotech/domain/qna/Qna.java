package com.kolon.biotech.domain.qna;

import com.kolon.biotech.domain.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QNA")
public class Qna extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QNA_ID", nullable = false)
    private Integer id;

    @Column(name = "QNA_CATE", nullable = false,length = 60)
    private String qnaCate;

    @Column(name = "USER_NAME", nullable = false,length = 300)
    private String userName;

    @Column(name = "PHONE", nullable = false,length = 30)
    private String phone;

    @Column(name = "USER_EMAIL", nullable = false,length = 300)
    private String userEmail;

    @Column(name = "USER_COMPANY", nullable = false,length = 900)
    private String userCompany;

    @Column(name = "USER_CONTENTS", nullable = false,length = 3000)
    private String userContents;

    @Column(name = "USER_POSITION", nullable = false,length = 300)
    private String userPosition;

    @Column(name="COUNTRY",length = 210)
    private String country;

    @Column(name = "ANSWER_DATE")
    private LocalDateTime answerDate;

    @Column(name = "ANSWER_TITLE",length = 900)
    private String answerTitle;

    @Column(name = "ANSWER_CONTENTS",columnDefinition = "TEXT")
    private String answerContents;

    @Column(name = "SEND_EMAIL",length = 50)
    private String sendEmail;

    @Column(name = "FILE_NAME",length = 300)
    private String fileName;

    @Column(name = "FILE_EXT",length = 6)
    private String fileExt;

    @Column(name = "FILE_LENGTH",length = 10)
    private String fileLength;

    @Column(name = "URI_PATH",length = 500)
    private String uriPath;

    @Column(name = "FILE_PATH",length = 500)
    private String filePath;

    @Column(name = "DEL_YN", length = 1)
    private String delYn;

}
