package com.dicemy.educenter.service;

import com.dicemy.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-13
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);
}
