package com.dicemy.eduservice.client;

import com.dicemy.commonutils.R;
import com.dicemy.eduservice.client.Impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/delect-batch")
    public R delectBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
