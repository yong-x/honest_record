package com.xy.honest_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    /*
    * 对于实体的属性字段，只有第一个字母和第二个字母全部都是小写的情况下，Jackson在序列化和反序列化时才能够找到其 set 方法。
    * 所以此处对于不满足上述规则的属性字段，必须显示使用 @JsonProperty 注解标明其是一个属性。否则，Jackson不能正确序列化该属性。
    * 在使用 @Requestbody 等注解时，不能把json对象属性dId 装配到 本属性上。
    * */
    @JsonProperty("dId")
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
     * 在职状态,0在职，1离职
     */
    private Integer workState;

    /**
     * 所属角色编号
     */
    @JsonProperty("rId")
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
     * 审核状态，0表示未审核，1表示已经审核
     */
    private Integer checkState;

    /**
     * 删除状态,0表示未删除，1表示已删除
     */
    private Integer deleted;


    /*
    * 自添加字段必须 @TableField(exist = false) 注解标明
    * */
    @TableField(exist = false)
    private Role role;

}
