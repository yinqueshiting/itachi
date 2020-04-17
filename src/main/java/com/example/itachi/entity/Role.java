package com.example.itachi.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * 角色表(Role)实体类
 *
 * @author makejava
 * @since 2020-04-17 16:58:34
 */
@TableName("role")
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -60340524206884596L;
    /**
    * 自增主键
    */
    private Integer id;
    /**
    * 角色代号
    */
    private String roleMark;
    /**
    * 角色名称,用作描述
    */
    private String roleName;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 状态0正常
    */
    private String status;







}