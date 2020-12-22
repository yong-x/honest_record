package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 教职工
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Faculty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 职工号
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 所属部门编号
     */
    private Integer dId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 职务
     */
    private String position;

    /**
     * 参加工作起始年份
     */
    private Integer workStartTime;

    /**
     * 在职状态
     */
    private Integer workState;

    /**
     * 所属角色编号
     */
    private Integer rId;

    /**
     * 用户创建时间
     */
    private Date createTime;

    /**
     * 用户修改时间
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
