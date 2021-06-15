package com.tianhan.cloud.other.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author NieAnTai
 * @Date 2021/4/21 11:45 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@Service
public class DoubleService {
    @Resource
    private OneService service;
}
