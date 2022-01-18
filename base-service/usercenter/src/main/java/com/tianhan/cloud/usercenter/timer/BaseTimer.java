package com.tianhan.cloud.usercenter.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Author NieAnTai
 * @Date 2022/1/12 5:01 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Slf4j
@Component
public class BaseTimer {
    private final static Random R = new Random(1000);
    private final static Random B = new Random(1);

    @Scheduled(cron = "0 0/2 *  * * ? ")
    public void run() {
        if(B.nextInt(2) == 1) {
            log.info("定期打印随机数值: {}", R.nextInt());
        } else {
            throw new RuntimeException("随机抛出异常");
        }
    }
}
