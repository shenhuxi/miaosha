package com.zpself.module.controller;

import com.zpself.util.pictureVerification.VerifyImage;
import com.zpself.util.pictureVerification.VerifyImageUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/6/15 11:47
 * @Modified By
 */
@RestController
public class PictureVerificationController {

    @GetMapping("/getImagBASE64")
    public Object getImagBASE64(){
        try {
            String PATH ="C:\\Users\\ying\\Desktop\\脸测试库\\刘亦菲\\"+ System.currentTimeMillis();
            String FILE ="C:\\Users\\ying\\Desktop\\脸测试库\\刘亦菲\\2.jpg" ;
            FileUtils.forceMkdir(new File(PATH));
            VerifyImage verifyImage = VerifyImageUtil.getVerifyImage(FILE);
            VerifyImageUtil.writeImg(VerifyImageUtil.base64StringToImage(verifyImage.getSrcImage()),PATH+"\\"+System.currentTimeMillis()+".jpg");
            VerifyImageUtil.writeImg(VerifyImageUtil.base64StringToImage(verifyImage.getCutImage()),PATH+"\\"+System.nanoTime()+".jpg");
            return  verifyImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
