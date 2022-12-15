package com.dicemy.educenter.controller;


import com.dicemy.commonutils.JwtUtils;
import com.dicemy.commonutils.R;
import com.dicemy.commonutils.ordervo.UcenterMemberOrder;
import com.dicemy.educenter.entity.UcenterMember;
import com.dicemy.educenter.entity.vo.RegisterVo;
import com.dicemy.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author dicemy
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/login")
    public R login(@RequestBody UcenterMember member){
        String token = ucenterMemberService.login(member);
        return R.ok().data("token", token);
    }

    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        member.setPassword(null);
        return R.ok().data("userInfo", member);
    }

    @PostMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = ucenterMemberService.countRegister(day);
        return R.ok().data("countRegister", count);
    }
}

