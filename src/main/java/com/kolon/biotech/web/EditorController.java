package com.kolon.biotech.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Slf4j
@RestController
public class EditorController {

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;

    @RequestMapping(value="/editor/ckUpload")
    @ResponseBody
    public void ckUpload(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload) throws Exception{

        log.debug("ckUpload 진입 =========================================1");
        // 랜덤 문자 생성
        UUID uid = UUID.randomUUID();
        OutputStream out = null;
        PrintWriter printWriter = null;
        // 인코딩
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html;charset=utf-8");
        try {
            String fileName = upload.getOriginalFilename();
            // 파일 이름 가져오기
            byte[] bytes = upload.getBytes();
            // 업로드 경로
            String ckUploadPath = targetRootLocation + File.separator + "editor" + File.separator + uid + "_" + fileName;
            out = new FileOutputStream(new File(ckUploadPath));
            out.write(bytes);
            out.flush();
            // out에 저장된 데이터를 전송하고 초기화
            String callback = req.getParameter("CKEditorFuncNum");
            printWriter = res.getWriter();
            String fileUrl = uriPath+"/editor/" + uid + "_" + fileName;
            // 작성화면
            // 업로드시 메시지 출력
            printWriter.println("<script type='text/javascript'>" + "window.parent.CKEDITOR.tools.callFunction(" + callback+",'"+ fileUrl+"','이미지를 업로드하였습니다.')" +"</script>");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                } if(printWriter != null) {
                    printWriter.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }
}
