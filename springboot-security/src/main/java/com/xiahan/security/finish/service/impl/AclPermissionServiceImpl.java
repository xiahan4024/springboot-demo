package com.xiahan.security.finish.service.impl;

import com.xiahan.security.finish.entity.AclPermission;
import com.xiahan.security.finish.entity.dto.RolePermission;
import com.xiahan.security.finish.mapper.AclPermissionMapper;
import com.xiahan.security.finish.service.AclPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiahan.util.PageUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author xiahan
 * @since 2021-03-01
 */
@Service
public class AclPermissionServiceImpl extends ServiceImpl<AclPermissionMapper, AclPermission> implements AclPermissionService {

  @Override
  public PageUtils queryPage(long current, long limit, Map<String, Object> params) {
      Page<AclPermission> page = new Page<>(current,limit);
      baseMapper.selectPage(page, null);
      return new PageUtils(page);
   }

    @Override
    public List<RolePermission> getRolePermission() {
        return baseMapper.getRolePermission();
    }
}
