package com.xiahan.shiro.finish.service.impl;

import com.xiahan.shiro.finish.entity.AclRole;
import com.xiahan.shiro.finish.mapper.AclRoleMapper;
import com.xiahan.shiro.finish.service.AclRoleService;
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
 * @since 2021-03-07
 */
@Service
public class AclRoleServiceImpl extends ServiceImpl<AclRoleMapper, AclRole> implements AclRoleService {

  @Override
  public PageUtils queryPage(long current, long limit, Map<String, Object> params) {
      Page<AclRole> page = new Page<>(current,limit);
      baseMapper.selectPage(page, null);
      return new PageUtils(page);
   }
}
