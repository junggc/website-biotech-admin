package com.kolon.biotech.domain.prbinfo;

import com.kolon.biotech.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRBINFO")
public class Prbinfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRBINFO_ID", nullable = false)
    private Integer id;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "USE_YN", length = 1)
    private String useYn;

}
