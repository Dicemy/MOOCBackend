package com.dicemy.eduorder.controller;


import com.dicemy.commonutils.R;
import com.dicemy.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author dicemy
 * @since 2022-12-14
 */
@RestController
@RequestMapping("/edeorder/paylog")
//@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    public R quarryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.quarryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中...");
    }
}

