package com.dicemy.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {
        String title = "6 - What If I Want to Move Faster";
        String filename = "G:\\6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest("LTAI5t69tvQt9uyCoBnTdocX", "J3fdCTw4WuqUJhzpJ8aALc8rRBo6nU", title, filename);
        request.setPartSize(2 * 1024 * 1024L);
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if(response.isSuccess()) {
            System.out.println("VideoId=" + response.getVideoId());
        } else {
            System.out.println("VideoId=" + response.getVideoId());
            System.out.println("ErrorCode=" + response.getCode());
            System.out.println("ErrorMessage=" + response.getMessage());
        }
    }

    public static void getPlayAuth() throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t69tvQt9uyCoBnTdocX", "J3fdCTw4WuqUJhzpJ8aALc8rRBo6nU");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("86ce46b9c32d4036810324b6c431396d");
        response = client.getAcsResponse(request);
        System.out.println("playauth:" + response.getPlayAuth());
    }

    public static void getPlayURL() throws ClientException{
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t69tvQt9uyCoBnTdocX", "J3fdCTw4WuqUJhzpJ8aALc8rRBo6nU");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        request.setVideoId("86ce46b9c32d4036810324b6c431396d");
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        System.out.println("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
