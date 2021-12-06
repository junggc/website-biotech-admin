package com.kolon.biotech.domain.mainpop;

import com.kolon.biotech.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MAINPOP")
public class Mainpop extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAINPOP_ID", nullable = false)
    private Integer id;

    @Column(name = "DISP_YN",nullable = false,length = 1)
    private String dispYn;

    @Column(name = "LANG_KO_YN",nullable = false,length = 1)
    private String langKoYn;

    @Column(name = "TITLE",nullable = false,length = 300)
    private String title;

    @Column(name = "POP_IMG_NAME",length = 300)
    private String popImgName;

    @Column(name = "POP_IMG_PATH",length = 500)
    private String popImgPath;

    @Column(name = "POP_IMG_REAL_PATH",length = 600)
    private String popImgRealPath;

    @Column(name = "POP_IMG_LINK",length = 4000)
    private String popImgLink;

    @Column(name = "POP_IMG_LINK_TARGET",length = 10)
    private String popImgLinkTarget;

    @Column(name = "POP_IMG_EXT", length = 6)
    private String popImgExt;

    @Column(name = "POP_IMG_SIZE", length = 10)
    private String popImgLength;

    @Column(name = "WIDTH_POINT",length = 5)
    private String widthPoint;

    @Column(name = "HEIGHT_POINT",length = 5)
    private String heightPoint;

    @Column(name = "DISP_START_DAY",length = 10)
    private String dispStartDay;

    @Column(name = "DISP_START_TIME",length = 2)
    private String dispStartTime;

    @Column(name = "DISP_END_DAY",length = 10)
    private String dispEndDay;

    @Column(name = "DISP_END_TIME",length = 2)
    private String dispEndTime;

//    @Builder
//    public Mainpop(String dispYn,
//                  String langKoYn,
//                  String title,
//                  String PopImgPath,
//                  String PopImgLink,
//                  String PopImgLinkTarget,
//                  String WidthPoint,
//                  String HeightPoint,
//                  String DispStartDay,
//                  String DispStartTime,
//                  String DispEndDay,
//                  String DispEndTime){
//        this.dispYn=dispYn;
//        this.langKoYn=langKoYn;
//        this.title=title;
//        this.PopImgPath=PopImgPath;
//        this.PopImgLink=PopImgLink;
//        this.PopImgLinkTarget=PopImgLinkTarget;
//        this.WidthPoint=WidthPoint;
//        this.HeightPoint=HeightPoint;
//        this.DispStartDay=DispStartDay;
//        this.DispStartTime=DispStartTime;
//        this.DispEndDay=DispEndDay;
//        this.DispEndTime=DispEndTime;
//
//    }
}
