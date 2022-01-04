package com.kolon.biotech.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class XssRequest extends HttpServletRequestWrapper {

    protected static final Logger LOGGER = LoggerFactory.getLogger(XssRequest.class);

    private byte[] body;

    public XssRequest(HttpServletRequest request) {
        super(request);
        LOGGER.debug( "XssRequest "+request.getRequestURI());
    }

    @Override
    public String getParameter(String key) {
        LOGGER.debug( "getParameter ");
        String value = null;
        try {
            value = super.getParameter(key);
            if (value != null) {
                value = xssReplace(key);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String key) {
        LOGGER.debug( "getParameterValues ");
        String[] values =super.getParameterValues(key);
        try {
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = xssReplace(key);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return values;
    }

    @Override
    public Map<String, String[]>  getParameterMap(){
        LOGGER.debug( "getParameterMap ");
        Map<String, String[]>  paramMap = super.getParameterMap();
        Map<String, String[]>  newFilteredParamMap = new HashMap<String, String[]>();

        Set<Map.Entry<String, String[]>> entries = paramMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String paramName = entry.getKey();
            Object[] valueObj = (Object[])entry.getValue();
            String[] filteredValue = new String[valueObj.length];
            for (int index = 0; index < valueObj.length; index++) {
                filteredValue[index] = xssReplace(paramName);
            }

            newFilteredParamMap.put(entry.getKey(), filteredValue);
        }

        return newFilteredParamMap;
    }

    @Override
    public ServletInputStream getInputStream(){
        try {
            InputStream is = super.getInputStream();
            if (is != null) {
                StringBuffer sb = new StringBuffer();
                while(true) {
                    int data = is.read();
                    if (data < 0) break;
                    sb.append((char) data);
                }

                String result = doWork(new String(sb.toString().getBytes("8859_1"), "utf-8"));
                body = result.getBytes(StandardCharsets.UTF_8);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

            return new ServletInputStream() {

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {}

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return null;
    }

    public byte[] getContentAsByteArray() {
        return this.body;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return xssReplace(value);

    }

    private String doWork(String job) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < job.length(); i++) {
            switch (job.charAt(i)) {
                case '(':
                    sb.append("&#40;"); break;
                case ')':
                    sb.append("&#41;"); break;
                case '<':
                    sb.append("&#60;"); break;
                case '>':
                    sb.append("&#62;"); break;
                case '\'':
                    sb.append("&#39;"); break;
                case '\"':
                    sb.append("&#34;"); break;
                default:
                    sb.append(job.charAt(i));
            }
        }
        return sb.toString();
    }

    private String xssReplace(String key){
        String rslt   = (super.getParameter(key) == null) ? "" : super.getParameter(key);
        if(rslt != null)
            rslt = rslt.trim();
        LOGGER.debug("=======================================================");
        LOGGER.debug("= before key =["+key+"]     rslt =["+rslt+"]");

        if(
                rslt!=null &&
                !rslt.equals("")
        )
        {
            rslt = rslt.replaceAll("<","&#60;");
            rslt = rslt.replaceAll(">","&#62;");
            rslt = rslt.replaceAll("\\(","&#40;");
            rslt = rslt.replaceAll("\\)","&#41;");
            rslt = rslt.replaceAll("\'","&#39;");
            rslt = rslt.replaceAll("\"","&#34;");

            rslt = rslt.replaceAll("(?i)cookie", "cook1e");
            rslt = rslt.replaceAll("(?i)document", "d0cument");
            rslt = rslt.replaceAll("(?i)<script", "&lt;script");
            rslt = rslt.replaceAll("(?i)<alert", "&lt;alert");
            rslt = rslt.replaceAll("(?i)&", "&amp;");
            rslt = rslt.replaceAll("(?i)script", "scr1pt");
            rslt = rslt.replaceAll("(?i)alert", "a1ert");
            //#&;

        }

        LOGGER.debug("= after  key =["+key+"]     rslt =["+rslt+"]");
        LOGGER.debug("=======================================================");

        return rslt;
    }


}
