package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 通知公告
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通知编号
     */
    @TableId(value = "n_id", type = IdType.AUTO)
    @JsonProperty("nId")
    private Integer nId;

    /**
     * 通知发布人
     */
    private Integer publishUserId;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知类型
     */
    @JsonProperty("nType")
    private Integer nType;


}
