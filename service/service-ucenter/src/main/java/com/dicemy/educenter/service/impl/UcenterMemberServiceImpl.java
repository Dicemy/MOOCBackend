package com.dicemy.educenter.service.impl;

import com.dicemy.educenter.entity.UcenterMember;
import com.dicemy.educenter.mapper.UcenterMemberMapper;
import com.dicemy.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public String login(UcenterMember member) {
        return null;
    }
}
