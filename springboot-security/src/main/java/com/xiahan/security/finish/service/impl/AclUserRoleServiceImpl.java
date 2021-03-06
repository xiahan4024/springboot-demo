package com.xiahan.security.finish.service.impl;

import com.xiahan.security.finish.entity.AclUserRole;
import com.xiahan.security.finish.mapper.AclUserRoleMapper;
import com.xiahan.security.finish.service.AclUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiahan.util.PageUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiahan
 * @since 2021-03-01
 */
@Service
public class AclUserRoleServiceImpl extends ServiceImpl<AclUserRoleMapper, AclUserRole> implements AclUserRoleService {

  @Override
  public PageUtils queryPage(long current, long limit, Map<String, Object> params) {
      Page<AclUserRole> page = new Page<>(current,limit);
      baseMapper.selectPage(page, null);
      return new PageUtils(page);
   }
}
