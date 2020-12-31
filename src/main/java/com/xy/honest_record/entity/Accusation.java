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
 * 举报信息
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Accusation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 举报信息编号
     */
    @TableId(value = "a_id", type = IdType.AUTO)
    @JsonProperty("aId")
    private Integer aId;

    /**
     * 问题分类
     */
    private Integer ptId;

    /**
     * 问题领域
     */
    private Integer pfId;

    /**
     * 举报类型
     */
    private Integer atId;

    /**
     * 被举报人职工号
     */
    private Integer accusedUserId;

    /**
     * 举报人姓名
     */
    private String accuserName;

    /**
     * 举报人联系方式
     */
    private String accuserPhone;

    /**
     * 举报时间
     */
    private Date accuseDate;

    /**
     * 受理时间
     */
    private Date dealDate;

    /**
     * 受理人编号
     */
    private Integer dealerUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 审核状态
     */
    private Integer checkState;

    /**
     * 删除状态
     */
    private Integer deleted;


}
