package com.dicemy.eduservice.client.Impl;

import com.dicemy.commonutils.R;
import com.dicemy.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R delectBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
