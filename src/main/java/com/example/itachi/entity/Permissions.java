package com.example.itachi.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * 权限表(Permissions)实体类
 *
 * @author makejava
 * @since 2020-04-17 17:01:35
 */
@TableName("permissions")
@Data
public class Permissions implements Serializable {
    private static final long serialVersionUID = 609945124080073183L;
    
    private Integer id;
    /**
    * 权限名称
    */
    private String permissionsName;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 状态0正常
    */
    private String status;
    /**
    * 父节点编号（用于记录按钮在哪个页面）
    */
    private Integer parentId;
    /**
    * 1一级（侧边栏）2（页面上的按钮，parent_id有具体的值）
    */
    private String type;
    
    private String describe;









}