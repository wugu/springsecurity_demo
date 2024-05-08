package com.pzhu.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;
import java.util.HashMap;

public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HashMap result = new HashMap<>();
        result.put("code",-1);//注销成功
        result.put("message", "该账号已从其他设备登录");
        //将结果转换成json字符串
        String json = JSON.toJSONString(result);

        HttpServletResponse response = event.getResponse();
        //返回json数据到前端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);

    }
}
