package com.tianhan.cloud.common.web.pager;

import com.tianhan.cloud.common.core.ResponseResult;

/**
 * @Author NieAnTai
 * @Date 2021/3/31 11:14 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
public class DataPagerResult extends ResponseResult {
    protected long count;

    public DataPagerResult(Pager pager) {
        super(200, "请求成功!", pager.getPageData());
        this.count = pager.getTotalPages();
    }
}
