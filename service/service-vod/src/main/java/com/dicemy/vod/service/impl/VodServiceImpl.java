package com.dicemy.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.dicemy.servicebase.exceptionhandler.CustomException;
import com.dicemy.vod.service.VodService;
import com.dicemy.vod.utils.ConstantVodUtils;
import com.dicemy.vod.utils.InitVodClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try{
            String filename = file.getOriginalFilename();
            String title = filename.substring(0, filename.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
//            log.info(ConstantVodUtils.ACCESS_KEY_ID + " : " + ConstantVodUtils.ACCESS_KEY_SECRET);
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, filename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
                log.info(response.getCode());
                log.info(response.getMessage());
            }
            return videoId;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeAlyVideo(String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(20001, "删除视频失败");
        }
    }

    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String id = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(20001, "删除视频失败");
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        String join = StringUtils.join(list.toArray(), ",");
        System.out.println(join);
    }
}
