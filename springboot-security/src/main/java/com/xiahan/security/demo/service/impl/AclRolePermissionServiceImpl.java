package com.xiahan.security.demo.service.impl;

import com.xiahan.security.demo.entity.AclRolePermission;
import com.xiahan.security.demo.mapper.AclRolePermissionMapper;
import com.xiahan.security.demo.service.AclRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiahan.util.PageUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author xiahan
 * @since 2021-02-24
 */
@Service
public class AclRolePermissionServiceImpl extends ServiceImpl<AclRolePermissionMapper, AclRolePermission> implements AclRolePermissionService {

  @Override
  public PageUtils queryPage(long current, long limit, Map<String, Object> params) {
      Page<AclRolePermission> page = new Page<>(current,limit);
      baseMapper.selectPage(page, null);
      return new PageUtils(page);
   }
}
