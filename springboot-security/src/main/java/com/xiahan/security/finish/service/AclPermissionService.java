package com.xiahan.security.finish.service;

import com.xiahan.security.finish.entity.AclPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiahan.security.finish.entity.dto.RolePermission;
import com.xiahan.util.PageUtils;

import java.util.List;
import java.util.Map;
/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author xiahan
 * @since 2021-03-01
 */
public interface AclPermissionService extends IService<AclPermission> {

    PageUtils queryPage(long current, long limit, Map<String, Object> params);

    List<RolePermission> getRolePermission();
}
