package com.dicemy.eduorder.service.impl;

import com.dicemy.commonutils.ordervo.CourseWebVoOrder;
import com.dicemy.commonutils.ordervo.UcenterMemberOrder;
import com.dicemy.eduorder.client.EduClient;
import com.dicemy.eduorder.client.UcenterClient;
import com.dicemy.eduorder.entity.Order;
import com.dicemy.eduorder.mapper.OrderMapper;
import com.dicemy.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dicemy.eduorder.utils.OrderNoUtil;
import com.dicemy.servicebase.exceptionhandler.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-14
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public String createOrders(String courseId, String memberId) {
//        log.info("memberId = " + memberId);
        if (memberId==null) {
            throw new CustomException(20001, "请先登录");
        }
        UcenterMemberOrder member = ucenterClient.getUserInfoOrder(memberId);
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(member.getId());
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());

        order.setStatus(0);
        order.setPayType(1);

        this.save(order);
        return order.getOrderNo();
    }
}
