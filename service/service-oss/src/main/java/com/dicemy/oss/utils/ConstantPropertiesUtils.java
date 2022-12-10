package com.dicemy.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    @Value("${qiniuyun.oss.file.keyid}")
    private String keyId;
    @Value("${qiniuyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${qiniuyun.oss.file.bucketname}")
    private String bucketName;
    @Value("${qiniuyun.oss.file.endpoint}")
    private String endpoint;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String END_POINT;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
        END_POINT = endpoint;
    }
}
