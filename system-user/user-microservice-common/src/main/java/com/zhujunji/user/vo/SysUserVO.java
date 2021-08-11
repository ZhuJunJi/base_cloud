package com.zhujunji.user.vo;

import lombok.Data;

import javax.management.relation.Role;
import java.io.Serializable;

/**
 *
 * @author J.zhu
 * @date 2019/7/11
 */
@Data
public class SysUserVO implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮件
     */
    private String email;

    /**
     * 状态（0：锁定，1：解锁）
     */
    private Integer status = 0;

}
