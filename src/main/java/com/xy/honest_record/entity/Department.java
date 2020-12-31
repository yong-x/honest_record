package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    @TableId(value = "d_id", type = IdType.AUTO)
    @JsonProperty("dId")
    private Integer dId;

    /**
     * 部门名称
     */
    @JsonProperty("dName")
    private String dName;

    /**
     * 联系方式
     */
    @JsonProperty("dPhone")
    private String dPhone;

    /**
     * 联系地址
     */
    @JsonProperty("dAddress")
    private String dAddress;

    /**
     * 部门信息创建时间
     */
    private Date createTime;

    /**
     * 部门信息修改时间
     */
    private Date updateTime;

    /**
     * 删除状态
     */
    private Integer deleted;


}
