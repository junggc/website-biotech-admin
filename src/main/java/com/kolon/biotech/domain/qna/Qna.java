package com.kolon.biotech.domain.qna;

import com.kolon.biotech.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QNA")
public class Qna extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "division", nullable = false,length = 1)
    private String division;

    @Column(name = "username", nullable = false,length = 300)
    private String username;

    @Column(name = "phone", nullable = false,length = 20)
    private String phone;

    @Column(name = "useremail", nullable = false,length = 100)
    private String useremail;

    @Column(name = "usertitle", nullable = false,length = 900)
    private String usertitle;

    @Column(name = "usercontents", nullable = false,length = 3000)
    private String usercontents;

    @Column(name = "userfile", nullable = false,length = 100)
    private String userfile;

    @Column(name = "answer_date",length = 30)
    private String answer_date;

    @Column(name = "answer_title",length = 900)
    private String answer_title;

    @Column(name = "answer_contents",columnDefinition = "TEXT")
    private String answer_contents;

    @Column(name = "send_email",length = 50)
    private String send_email;

}
