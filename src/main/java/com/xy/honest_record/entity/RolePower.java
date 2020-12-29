package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色权限
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RolePower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    //@TableId(value = "r_id", type = IdType.AUTO)
    @JsonProperty("rId")
    private Integer rId;

    /**
     * 权限编号
     */

    @JsonProperty("pId")
    private String pId;


}
