package com.kolon.biotech.web;

import com.dext5.DEXT5Handler;
import com.oreilly.servlet.MultipartRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class EditorController {

    @Value("${resources.uri_path}")
    private String uriPath;

    @Value("${resources.location}")
    private String targetRootLocation;

    @RequestMapping(value="/editorUpload")
    public void editorUpload(HttpServletRequest request, HttpServletResponse response)throws Exception{

        String _allowFileExt = "gif, jpg, jpeg, png, bmp, wmv, asf, swf, avi, mpg, mpeg, mp4, txt, doc, docx, xls, xlsx, ppt, pptx, hwp, zip, pdf,flv";
        int upload_max_size = 2147483647;

        DEXT5Handler DEXT5 = new DEXT5Handler();

        ServletContext application = request.getServletContext();
        String[] allowUploadDirectoryPath = { targetRootLocation+"/editor" };

        DEXT5.SetAllowUploadDirectoryPath(allowUploadDirectoryPath);
        DEXT5.SetTempRealPath("/data/upload/temp");

        String result = DEXT5.DEXTProcess(request, response, application, _allowFileExt, upload_max_size);

        //if(!result.equals("")) {
            response.setContentType("text/html");
            ServletOutputStream out = response.getOutputStream();
            out.print("123123");
            out.print(result);
            out.close();
        //}
    }
}
