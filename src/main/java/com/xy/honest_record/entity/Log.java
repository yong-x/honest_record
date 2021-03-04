package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.control.Alert;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 日志
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
    @TableId(value = "l_id", type = IdType.AUTO)
    @JsonProperty("lId")
    private Long lId;

    /**
     * 操作名称
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private Date operatorTime;

    /**
     * 操作人编号
     */
    private Integer operatoerUserId;

    /**
     * 操作主机ip
     */
    private String hostIp;

    /**
     * 日志类型,操作资源编号(登录日志时为用户编号，操作日志时为操作资源编号)
     */
    private String logType;

}
