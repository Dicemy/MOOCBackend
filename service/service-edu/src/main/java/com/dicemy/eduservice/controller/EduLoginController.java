package com.dicemy.eduservice.controller;

import com.dicemy.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
@Api(description = "登录管理")
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
public class EduLoginController {
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "http://cdn.dicemy.work/202212041510677.png");
    }
}
