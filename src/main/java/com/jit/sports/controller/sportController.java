package com.jit.sports.controller;

import com.jit.sports.entry.sportInfo;
import com.jit.sports.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/sport")
@RestController
public class sportController {
    @Resource
    UserService userService;

    //开始一次运动
    @RequestMapping("/startSport")
    public String startSport(@RequestParam (value = "userName")String userName) {
        String tag = userName + System.currentTimeMillis();
        return tag;
    }

    //结束一次运动
    @RequestMapping("/overSport")
    public void overSport(@RequestParam (value = "tag")String tag) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = ft.format(new Date());
        userService.insertSport(tag, "shiyu", time, time,
        100, 20, 20, 80,
        20, 3 , 0);
    }

    //查看所有运动
    @RequestMapping("/mySports")
    public sportInfo[] mySports(@RequestParam (value = "userName")String userName) {

        return null;
    }

    //查看运动详情
    @RequestMapping("/detail")
    public void detail(@RequestParam (value = "tag")String tag) {


    }


}
