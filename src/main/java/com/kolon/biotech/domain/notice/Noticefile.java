package com.kolon.biotech.domain.notice;

import com.kolon.biotech.domain.BaseTimeEntity;
import groovyjarjarpicocli.CommandLine;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTICE_FILE")
@DynamicInsert
@DynamicUpdate
public class Noticefile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID", nullable = false)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "NOTICE_ID", nullable = false)
//    private Notice notice;

    @Column(name = "NOTICE_ID")
    private Integer noticeId;

    @Column(name = "FILE_NAME", nullable = false, length = 300)
    private String fileName;

    @Column(name = "FILE_EXT", nullable = false, length = 6)
    private String fileExt;

    @Column(name = "FILE_PATH", nullable = false, length = 500)
    private String filePath;

    @Column(name = "URI_PATH", nullable = false, length = 500)
    private String uriPath;

    @Column(name = "FILE_LENGTH", nullable = false, length = 10)
    private String fileLength;

//    @Builder
//    public Noticefile(Notice notice, String fileName, String fileExt, String filePath, String fileLength){
//        this.notice = notice;
//        this.fileName = fileName;
//        this.fileExt = fileExt;
//        this.filePath = filePath;
//        this.fileLength = fileLength;
//
//    }
//
//    public void setNotice(Notice notice){
//        this.notice = notice;
//    }

    @Builder
    public Noticefile(Integer noticeId, String fileName, String fileExt, String filePath,String uriPath, String fileLength){
        this.noticeId = noticeId;
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.filePath = filePath;
        this.uriPath = uriPath;
        this.fileLength = fileLength;

    }
}
