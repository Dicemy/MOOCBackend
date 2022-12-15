package com.dicemy.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dicemy.commonutils.R;
import com.dicemy.staservice.client.UcenterClient;
import com.dicemy.staservice.entity.StatisticsDaily;
import com.dicemy.staservice.mapper.StatisticsDailyMapper;
import com.dicemy.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-15
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);
        sta.setDateCalculated(day);

        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setCourseNum(RandomUtils.nextInt(100, 200));

        this.save(sta);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = this.list(wrapper);

        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            date_calculatedList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
