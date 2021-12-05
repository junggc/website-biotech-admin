package com.kolon.biotech.domain.mainvisual;

import com.kolon.biotech.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MAINVISUAL")
public class Mainvisual extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAINVISUAL_ID", nullable = false)
    private Integer id;

    @Column(name = "DISP_YN", nullable = false, length = 1)
    private String dispYn;

    @Column(name = "LANG_KO_YN", nullable = false, length = 1)
    private String langKoYn;

    @Column(name = "TITLE", nullable = false, length = 300)
    private String title;

    @Column(name = "TYPE", nullable = false, length = 1)
    private String type;

    @Column(name = "PC_IMG_PATH", length = 500)
    private String pcImgPath;

    @Column(name = "PC_IMG_LINK",length = 4000)
    private String pcImgLink;

    @Column(name = "PC_IMG_TARGET",length = 7)
    private String pcImgTarget;

    @Column(name = "PC_IMG_REAL_PATH", length = 600)
    private String pcImgRealPath;

    @Column(name = "PC_IMG_NAME", length = 300)
    private String pcImgName;

    @Column(name = "PC_IMG_EXT", length = 6)
    private String pcImgExt;

    @Column(name = "PC_IMG_LENGTH", length = 10)
    private String pcImgLength;

    @Column(name = "MO_IMG_PATH",length = 500)
    private String moImgPath;

    @Column(name = "MO_IMG_LINK",length = 4000)
    private String moImgLink;

    @Column(name = "MO_IMG_TARGET",length = 7)
    private String moImgTarget;

    @Column(name = "MO_IMG_REAL_PATH", length = 600)
    private String moImgRealPath;

    @Column(name = "MO_IMG_NAME", length = 300)
    private String moImgName;

    @Column(name = "MO_IMG_EXT", length = 6)
    private String moImgExt;

    @Column(name = "MO_IMG_LENGTH", length = 10)
    private String moImgLength;

    @Column(name = "VIDEOS_WAY",length = 7)
    private String videosWay;

    @Column(name = "VIDEOS_URL",length = 4000)
    private String videosUrl;

    @Column(name = "VIDEOS_PATH",length = 500)
    private String videosPath;

    @Column(name = "VIDEOS_REAL_PATH", length = 600)
    private String videosRealPath;

    @Column(name = "VIDEOS_NAME", length = 300)
    private String videosName;

    @Column(name = "VIDEOS_EXT", length = 6)
    private String videosExt;

    @Column(name = "VIDEOS_LENGTH", length = 10)
    private String videosLength;

    @Column(name = "TOP_SUB_TXT",length = 1200)
    private String topSubTxt;

    @Column(name = "BOTTOM_MAIN_TXT",length = 2400)
    private String bottomMainTxt;

    @Column(name = "ORDER_SEQ")
    private int orderSeq;



//    @Builder
//    public Mainvisual(String dispYn,
//                      String langKoYn,
//                      String title,
//                      String type,
//                      String pcImgPath,
//                      String pcImgLink,
//                      String moImgPath,
//                      String moImgLink,
//                      String videosUrl,
//                      String videosPath,
//                      String topSubTxt,
//                      String bottomMainTxt,
//                      int orderSeq) {
//        this.dispYn=dispYn;
//        this.langKoYn=langKoYn;
//        this.title=title;
//        this.type=type;
//        this.pcImgPath=pcImgPath;
//        this.pcImgLink=pcImgLink;
//        this.moImgPath=moImgPath;
//        this.moImgLink=moImgLink;
//        this.videosUrl=videosUrl;
//        this.videosPath=videosPath;
//        this.topSubTxt=topSubTxt;
//        this.bottomMainTxt=bottomMainTxt;
//        this.orderSeq=orderSeq;
//    }
//
//    public void update(String dispYn,
//                       String langKoYn,
//                       String title,
//                       String type,
//                       String pcImgPath,
//                       String pcImgLink,
//                       String moImgPath,
//                       String moImgLink,
//                       String videosUrl,
//                       String videosPath,
//                       String topSubTxt,
//                       String bottomMainTxt) {
//        this.dispYn=dispYn;
//        this.langKoYn=langKoYn;
//        this.title=title;
//        this.type=type;
//        this.pcImgPath=pcImgPath;
//        this.pcImgLink=pcImgLink;
//        this.moImgPath=moImgPath;
//        this.moImgLink=moImgLink;
//        this.videosUrl=videosUrl;
//        this.videosPath=videosPath;
//        this.topSubTxt=topSubTxt;
//        this.bottomMainTxt=bottomMainTxt;
//    }
//
//    public void updateOrderSeq(int orderSeq) {
//        this.orderSeq=orderSeq;
//    }
}
