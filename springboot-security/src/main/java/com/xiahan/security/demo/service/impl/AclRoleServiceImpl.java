package com.xiahan.security.demo.service.impl;

import com.xiahan.security.demo.entity.AclRole;
import com.xiahan.security.demo.mapper.AclRoleMapper;
import com.xiahan.security.demo.service.AclRoleService;
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
 * @since 2021-02-24
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
