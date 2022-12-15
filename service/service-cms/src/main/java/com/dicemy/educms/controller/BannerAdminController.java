package com.dicemy.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dicemy.commonutils.R;
import com.dicemy.educms.entity.CrmBanner;
import com.dicemy.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author dicemy
 * @since 2022-12-12
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> crmBannerPage = new Page<CrmBanner>();
        bannerService.page(crmBannerPage, null);
        return R.ok().data("items", crmBannerPage.getRecords()).data("total", crmBannerPage.getTotal());
    }

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner crmBanner = bannerService.getById(id);
        return R.ok().data("item", crmBanner);
    }

    @PostMapping("/update")
    public R updateById(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}

