package com.dicemy.oss.service.impl;

import com.dicemy.oss.service.OssService;
import com.dicemy.oss.utils.ConstantPropertiesUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);

        String key = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        key = uuid + key;
        String datePath = new DateTime().toString("yyyy/MM/dd");
        key = datePath + "/" + key;


        try{
            InputStream inputStream = file.getInputStream();
            Auth auth = Auth.create(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            String upToken = auth.uploadToken(ConstantPropertiesUtils.BUCKET_NAME);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                String url = "http://" + ConstantPropertiesUtils.END_POINT + "/"+putRet.key;
                log.info(putRet.key, putRet.hash);
                return url;
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error(r.toString());
//                System.err.println(r.toString());
                try {
                    log.error(r.bodyString());
//                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        }catch (Exception ex) {
            //ignore
        }
        return null;
    }
}
