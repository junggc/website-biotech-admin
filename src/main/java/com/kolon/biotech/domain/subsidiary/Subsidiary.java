package com.kolon.biotech.domain.subsidiary;

import com.kolon.biotech.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SUBSIDIARY")
public class Subsidiary extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSIDIARY_ID", nullable = false)
    private Integer id;

    @Column(name = "CODE", length = 5)
    private String code;

    @Column(name = "NAME", length = 60)
    private String name;

    @Column(name = "NAME_EN", length = 120)
    private String nameEn;

    @Column(name = "URL", length = 100)
    private String url;

    @Column(name = "USE_YN", length = 1)
    private String useYn;

    @Column(name = "ORDER_SEQ")
    private String orderSeq;

//    @Builder
//    public Subsidiary(String code,
//                      String name,
//                      String nameEn,
//                      String url,
//                      String useYn,
//                      String orderSeq){
//        this.code=code;
//        this.name=name;
//        this.nameEn=nameEn;
//        this.url=url;
//        this.useYn=useYn;
//        this.orderSeq=orderSeq;
//
//    }

}
