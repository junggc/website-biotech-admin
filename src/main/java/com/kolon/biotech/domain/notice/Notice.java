package com.kolon.biotech.domain.notice;

import com.kolon.biotech.domain.BaseTimeEntity;
import com.kolon.biotech.domain.user.Member;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTICE")
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID", nullable = false)
    private Integer id;

    @Column(name="DISP_YN",nullable = false, length = 1)
    private String dispYn;

    @Column(name="DISP_START_DAY", length = 10)
    private String dispStartDay;

    @Column(name="DISP_START_TIME", length = 2)
    private String dispStartTime;

    @Column(name="LANG_KO_YN",nullable = false, length = 1)
    private String langKoYn;

    @Column(name="TITLE",nullable = false, length = 300)
    private String title;

    @Column(name="CONTENT", columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "noticeId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Noticefile> noticefileList = new ArrayList<>();

    @Builder
    public Notice(String dispYn, String dispStartDay, String dispStartTime, String langKoYn, String title, String content){
        this.dispYn = dispYn;
        this.dispStartDay = dispStartDay;
        this.dispStartTime = dispStartTime;
        this.langKoYn = langKoYn;
        this.title = title;
        this.content = content;

    }

}
