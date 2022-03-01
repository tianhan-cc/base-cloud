package com.tianhan.cloud.usercenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author NieAnTai
 * @Date 2021/3/31 2:26 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("dept")
@Data
public class DeptEntity extends Model<DeptEntity> {
    @TableId(type = IdType.AUTO)
    private String id;
    private String deptName;
    private String parentId;
    private Integer status;
}
