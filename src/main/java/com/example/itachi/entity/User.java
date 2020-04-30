package com.example.itachi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.itachi.util.validated.DeleteValidated;
import com.example.itachi.util.validated.InsertValidated;
import com.example.itachi.util.validated.SelectValidated;
import com.example.itachi.util.validated.UpdateValidated;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import sun.security.krb5.internal.Ticket;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * (Test)实体类
 *
 * @author makejava
 * @since 2020-04-08 11:42:42
 */
@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 675225418891068452L;
    /**
    * 主键 新增时不需要 删改查时需要做条件
    */
    @TableId(type = IdType.AUTO,value = "id")
    @Null(groups = InsertValidated.class)
    private Integer id;

    /**
     * 姓名，在增改时做验证
     */
    @Column(name = "name")
    @Size(min = 0,max = 20,message = "name cannot exceed 20 characters",groups = {InsertValidated.class, UpdateValidated.class})
    private String name;
    /**
    * 手机号，在增改时做验证
    */
    @Column(name = "phone")
    @Pattern(regexp = "^[1]+[3,5,8]+\\d{9}",message = "Enter the correct phone number",groups = {InsertValidated.class, UpdateValidated.class})
    private String phone;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;


    @Column(name = "password")
    @NotEmpty(groups = InsertValidated.class,message = "password cannot be null")
    @Size(min = 6,max = 16,groups = InsertValidated.class,message = "密码长度在6-16")
    private transient String password; //

    @Column(name = "password2")
    private String password2;

    @JsonIgnore
    private String salt;


    @Column(name = "user_id")
    @Null(groups = InsertValidated.class)
    @NotEmpty(message = "user_id cannot be null",groups = {SelectValidated.class,UpdateValidated.class, DeleteValidated.class})
    private String userId;


    @TableField(exist = false)
    /*condition : 预处理 WHERE 实体条件自定义运算规则
        @TableField(condition = SqlCondition.LIKE)
        private String name;
        输出 SQL 为：select 表 where name LIKE CONCAT('%',值,'%')
    */
    private List<Ticket> ticketList;

    @Column(name = "status")
    private String status;
}