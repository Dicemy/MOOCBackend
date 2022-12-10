package com.dicemy.demo;

import com.alibaba.excel.EasyExcel;
import com.dicemy.demo.excel.DemoData;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String filename = "D:\\01.xlsx";
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());
    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
