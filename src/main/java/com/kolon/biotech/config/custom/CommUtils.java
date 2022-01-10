package com.kolon.biotech.config.custom;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;

import javax.crypto.IllegalBlockSizeException;
import java.nio.channels.IllegalBlockingModeException;


@Component
public class CommUtils {

    public String getUnEscHtml(String target){
        return StringEscapeUtils.unescapeHtml4(target);
    }

    public String aes256Encode(String str) throws Exception{
        AES256Cipher a256 = AES256Cipher.getInstance();

        return a256.AES_Encode(str);
    }

    public String aes256Decode(String str) throws Exception{
        AES256Cipher a256 = AES256Cipher.getInstance();

        System.out.println("################"+str);
        String dStr = "";
        try{
            dStr = a256.AES_Decode(str);
        }catch (IllegalBlockSizeException ee){
            dStr = str;
        }catch (IllegalBlockingModeException eee){
            dStr = str;
        }
        return dStr;
    }
}
