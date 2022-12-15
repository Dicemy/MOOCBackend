package com.dicemy.eduorder.service;

import com.dicemy.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-14
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);

    Map<String, String> quarryPayStatus(String orderNo);

    void updateOrdersStatus(Map<String, String> map);
}
