package com.xiahan.security.finish.mapper;

import com.xiahan.security.finish.entity.AclPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiahan.security.finish.entity.dto.RolePermission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author xiahan
 * @since 2021-03-01
 */
public interface AclPermissionMapper extends BaseMapper<AclPermission> {

    List<RolePermission> getRolePermission();
}
