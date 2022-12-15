package com.dicemy.staservice.schedule;

import com.dicemy.staservice.service.StatisticsDailyService;
import com.dicemy.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService staService;
//    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 0 1 * * ?")
    public void  task1() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
