package com.dicemy.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.dicemy.msmservice.service.MsmService;
import com.dicemy.msmservice.utils.ConstantMsmUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean sendMsm(String code, String phone) {
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        if (StringUtils.isEmpty(phone)) {
            return false;
        }
//        log.info(ConstantMsmUtils.ACCESS_KEY_ID + " : " + ConstantMsmUtils.ACCESS_KEY_SECRET);
        DefaultProfile profile = DefaultProfile.getProfile("default", ConstantMsmUtils.ACCESS_KEY_ID, ConstantMsmUtils.ACCESS_KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "阿里云短信测试");
        request.putQueryParameter("TemplateCode","SMS_154950909");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
