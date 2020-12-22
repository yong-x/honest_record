package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 问题领域
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProblemField implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问题领域编号
     */
    @TableId(value = "pf_id", type = IdType.AUTO)
    private Integer pfId;

    /**
     * 问题领域名称
     */
    private String pfName;

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
