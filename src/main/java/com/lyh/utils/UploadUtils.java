package com.lyh.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import lombok.val;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by lyh on 2022/3/20.
 */
public class UploadUtils {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static final String ACCESS_KEY = "o3uMSp3wp56Q817dOKaoX6amznh0-e4EzaISlioh"; //这两个登录七牛
    private static final String SECRET_KEY = "fZz2aq0dct24p9uGhNzBSDja6_0SVr8CMwpwCJxO";
    //要上传的空间
    private static final String bucketName = "bsimgs2"; //对应要上传到七牛上
    private static final String domain = "rbfvta36h.hd-bkt.clouddn.com"; //对应要上传到七牛上
    //密钥配置
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    private static Configuration cfg = new Configuration(Region.huadong());
    private static UploadManager uploadManager = new UploadManager(cfg);

    // 采用默认策略，只需设置存储空间名
    public static String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    //上传图片实现
    public static String uploadFile(File file) {
        String filePath = file.getAbsolutePath();
        System.out.println(filePath);
        //Ky表示文件上传到服务器中的名称，为空的话默认为文件Hash值
        String key = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(".")) + System.currentTimeMillis() + filePath.substring(filePath.lastIndexOf("."));
        String upToken = auth.uploadToken(bucketName);
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.hash);
            System.out.println(putRet.key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String uploadFile(MultipartFile file) throws IOException {
        String filePath = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();
        //Ky表示文件上传到服务器中的名称，为空的话默认为文件Hash值
        String key = filePath.substring(0, filePath.lastIndexOf(".")) + System.currentTimeMillis() + filePath.substring(filePath.lastIndexOf("."));
        String upToken = auth.uploadToken(bucketName);
        String resKey = "";
        try {
            Response response = uploadManager.put(fileBytes, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            /*System.out.println(putRet.hash);
            System.out.println(putRet.key);*/
            resKey = URLEncoder.encode(putRet.key,"utf-8");
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return "http://rbfvta36h.hd-bkt.clouddn.com/"+ resKey;
    }

    public static List<String> uploadConsultantQualification(MultipartFile[] files) throws IOException {
        List<String> list = new ArrayList<>();
        if(files != null && files.length > 0){
            /*for(MultipartFile file : files){
                String url = uploadFile(file);
                list.add(url);
            }*/
            for(int i = 0 ; i < files.length ; i++){
                MultipartFile file = files[i];
                String url = uploadFile(file);
                list.add(url);
            }
        }
        return list;
    }
}
