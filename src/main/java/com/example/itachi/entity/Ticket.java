package com.example.itachi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (Ticket)实体类
 *
 * @author makejava
 * @since 2020-04-10 10:55:58
 */
@Data
@Entity
@TableName("ticket")
public class Ticket implements Serializable {
    private static final long serialVersionUID = -58121091175778865L;
    /**
    * ticket表自增主键
    */
    @TableId(type = IdType.AUTO,value = "id")
    private Integer id;
    /**
    * 票名
    */
   // @Column(name = "ticket_name")
    private String ticketName;
    /**
    * 数额，以分为单位
    */

    private Integer tickerAmount;
    /**
    * 外键，用户id
    */
    private String userId;



}