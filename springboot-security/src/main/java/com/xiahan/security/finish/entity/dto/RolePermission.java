package com.xiahan.security.finish.entity.dto;

import com.xiahan.security.finish.entity.AclRole;
import lombok.Data;

import java.util.List;

/**
 * @Auther: xiahan
 * @Date: 2021/3/2 20:45
 * @Description:
 */
@Data
public class RolePermission {

    private String url;

    private List<AclRole> roles;
}
