package com.dicemy.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dicemy.commonutils.JwtUtils;
import com.dicemy.commonutils.MD5;
import com.dicemy.educenter.entity.UcenterMember;
import com.dicemy.educenter.entity.vo.RegisterVo;
import com.dicemy.educenter.mapper.UcenterMemberMapper;
import com.dicemy.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dicemy.servicebase.exceptionhandler.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-13
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new CustomException(20001, "登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = this.getOne(wrapper);
        if (mobileMember == null) {
            throw new CustomException(20001, "登录失败");
        }
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new CustomException(20001, "密码错误");
        }
        if (mobileMember.getIsDisabled()) {
            throw new CustomException(20001, "该用户已被封禁，请联系管理员");
        }

        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new CustomException(20001, "注册失败");
        }
        if (!redisTemplate.opsForValue().get(mobile).equals(code)) {
            throw new CustomException(20001, "验证码错误");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        int count = this.count(wrapper);
        if (count > 0) {
            throw new CustomException(20001, "该号码已注册");
        }

        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("http://cdn.dicemy.work/202212141237722.gif");
        this.save(ucenterMember);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = this.getOne(wrapper);
        return member;
    }
}
