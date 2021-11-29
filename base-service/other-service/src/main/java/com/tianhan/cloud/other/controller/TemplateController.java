package com.tianhan.cloud.other.controller;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author NieAnTai
 * @Date 2021/11/1 3:39 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@RestController
@RequestMapping("/")
public class TemplateController {

    @PostMapping("/user/login")
    public Object login(@RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        response.put("token", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImFkbWluIiwiZXhwIjoxNjIxMjI0Mzk1LCJ1c2VySWQiOiIxIiwiaWF0IjoxNjIxMjIwNzk1fQ.KVkvjxahsgVXaq4_j65FJOW579DhOMDQBHAooUxiygM");
        response.put("UUID", "admin-26bb867e9e3440f2b7f9082a1c8cfd2e");
        response.put("userId", "1");
        response.put("version", "v1");
        return response;
    }

    @PostMapping("/north/online_data_get")
    public Object obtainMonitor(HttpServletRequest request, @RequestBody Map<String, Object> body) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> devices = new HashMap<>();
        List<Map<String, Object>> points = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> t = new HashMap<>();
            t.put("guid", "6F9619FF-8B86-D011-B42D-00C04FC91212");
            t.put("tag", "105021001000");
            t.put("value", 222.2);
            t.put("timestamp", 1468471315);
            points.add(t);
        }
        devices.put("guid", "6F9619FF-8B86-D011-B42D-00C04FC91211");
        devices.put("status", 1);
        devices.put("points", points);
        response.put("devices", devices);
        return new MonitorsVO(response);
    }

    @PostMapping("/user/north/logout")
    public String logout() {
        return "{\"error_code\":0,\"error_msg\":\"ok\",\"data\":{\"logout_time\":1468471315}}";
    }

    @Data
    static class LoginVO implements Serializable {
        private int resultCode = 200;
        private String resultMsg = "resultMsg";
        private Object data;

        public LoginVO(Object data) {
            this.data = data;
        }
    }

    @Data
    static class MonitorsVO implements Serializable {
        private int error_code = 0;
        private String error_msg = "ok";
        private Object devices;

        public MonitorsVO(Object devices) {
            this.devices = devices;
        }
    }
}
