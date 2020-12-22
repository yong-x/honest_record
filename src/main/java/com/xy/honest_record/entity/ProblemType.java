package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 问题分类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProblemType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问题分类编号
     */
    @TableId(value = "pt_id", type = IdType.AUTO)
    private Integer ptId;

    /**
     * 问题分类名称
     */
    private String ptName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除状态
     */
    private Integer deleted;


}
