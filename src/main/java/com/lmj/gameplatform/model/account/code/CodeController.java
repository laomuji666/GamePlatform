package com.lmj.gameplatform.model.account.code;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
    Code
    整合该包下所有功能,提供验证码操作的接口
    接口如下:
    1.getCodeKey:增加一个验证码,返回codeKey
    2.getCodePicture:通过codeKey获取codePicture
    3.verifyCode:通过codeKey和codeStr判断验证码是否正确
 */
@Controller
public class CodeController {

    //Map codeKey对应CodeData
    private Map<Long,CodeData> codeDataMap=new HashMap<>();

    //验证码有效时间 毫秒
    private static long codeValidTime=60000;

    //获取codeKey,创建验证码
    public Long getCodeKey(){
        Long codeKey=System.currentTimeMillis();
        codeDataMap.put(codeKey,new CodeData());
        return codeKey;
    }

    //根据codeKey获取验证码图片
    public byte[] getCodePicture(Long codeKey){
        return codeDataMap.get(codeKey).getCodePicture();
    }

    //验证验证码,无论是否验证成功都删除该验证码
    public boolean verifyCode(Long codeKey,String codeStr){
        CodeData codeData = codeDataMap.get(codeKey);
        codeDataMap.remove(codeKey);
        if (codeData!=null){
            return codeData.getCodeStr().toUpperCase().equals(codeStr.toUpperCase());
        } else {
            return false;
        }
    }

    //定时移除验证码
    @Scheduled(fixedRate = 5000)
    private void removeCodeData(){
        long nowTime=System.currentTimeMillis()-codeValidTime;
        Set<Long> longs = codeDataMap.keySet();
        Iterator<Long> iterator = longs.iterator();
        while (iterator.hasNext()){
            long time =iterator.next();
            if (time<nowTime) {
                iterator.remove();
            }
        }
    }

}
