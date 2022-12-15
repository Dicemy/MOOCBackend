package com.dicemy.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dicemy.commonutils.JwtUtils;
import com.dicemy.commonutils.R;
import com.dicemy.eduorder.entity.Order;
import com.dicemy.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author dicemy
 * @since 2022-12-14
 */
@RestController
@RequestMapping("/edeorder/order")
//@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderNo);
    }

    @GetMapping("/getOrderInfo/{orderid}")
    public R getOrderInfo(@PathVariable String orderid) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderid);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }
}

