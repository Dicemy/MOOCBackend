package com.dicemy.educenter.controller;

import com.dicemy.commonutils.JwtUtils;
import com.dicemy.educenter.entity.UcenterMember;
import com.dicemy.educenter.service.UcenterMemberService;
import com.dicemy.educenter.utils.ConstantWxUtils;
import com.dicemy.educenter.utils.HttpClientUtils;
import com.dicemy.servicebase.exceptionhandler.CustomException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Slf4j
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
                    );

            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token  = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);
            if (member == null) {
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );

                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap mapUserInfo = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) mapUserInfo.get("nickname");
                String headimgurl = (String) mapUserInfo.get("headimgurl");
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                member.setNickname(nickname);
                ucenterMemberService.save(member);
            }

            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;
        }catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(20001, "微信注册失败");
        }
    }

    @GetMapping("/login")
    public String getWxCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirect_url = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try{
            redirect_url = URLEncoder.encode(redirect_url, "utf-8");
        }catch (Exception e) {

        }
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirect_url,
                "dicemy");
        return "redirect:" + url;
    }
}
