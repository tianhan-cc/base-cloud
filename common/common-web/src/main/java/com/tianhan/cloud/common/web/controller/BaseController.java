package com.tianhan.cloud.common.web.controller;

import com.tianhan.cloud.common.core.ResponseResult;
import com.tianhan.cloud.common.core.exceptions.BusinessException;
import com.tianhan.cloud.common.core.exceptions.ConvertTypeException;
import com.tianhan.cloud.common.web.pager.DataPagerResult;
import com.tianhan.cloud.common.web.pager.Pager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author NieAnTai
 * @Date 2021/3/31 2:33 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 基础控制器异常拦截
 **/
@RestController
public class BaseController {

    protected @ResponseBody
    ResponseResult doJsonPagerOut(Pager pager){
        return new DataPagerResult(pager);
    }

    protected ResponseResult doDefaultMsg() {
        return new ResponseResult(HttpStatus.OK.value(), "操作成功!");
    }

    protected ResponseResult doJsonOut(Object data) {
        return new ResponseResult(HttpStatus.OK.value(), "操作成功!", data);
    }

    @ExceptionHandler(value = {Throwable.class})
    private ResponseResult throwableHandler() {
        return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求失败,请稍后再试!");
    }

    @ExceptionHandler(value = {BusinessException.class})
    private ResponseResult businessHandler(BusinessException e) {
        return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(value = {ConvertTypeException.class})
    private ResponseResult convertTypeHandler() {
        return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "类型转换失败!");
    }
}
