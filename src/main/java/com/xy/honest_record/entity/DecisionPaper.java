package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 决策书
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DecisionPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 决策书编号
     */
    @TableId(value = "dp_id", type = IdType.AUTO)
    private Integer dpId;

    /**
     * 决策书标题
     */
    private String title;

    /**
     * 回复单位名称
     */
    private String replyName;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 落款单位
     */
    private String inscribeName;

    /**
     * 落款时间
     */
    private Date inscribeTime;

    /**
     * 创建人编号
     */
    private Integer createdUserId;

    /**
     * 模板创建时间
     */
    private Date createTime;

    /**
     * 模板修改时间
     */
    private Date updateTime;


}
