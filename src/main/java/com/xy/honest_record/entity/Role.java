package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @TableId(value = "r_id", type = IdType.AUTO)
    @JsonProperty("rId")
    private Integer rId;

    /**
     * 角色名称
     */
    @JsonProperty("rName")
    private String rName;

    /**
     * 角色创建时间
     */
    private Date createTime;

    /**
     * 角色修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private List<Power> powerList;//该角色拥有的权限列表
}
