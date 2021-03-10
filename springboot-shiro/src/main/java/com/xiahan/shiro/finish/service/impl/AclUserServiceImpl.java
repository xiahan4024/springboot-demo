package com.xiahan.shiro.finish.service.impl;

import com.xiahan.shiro.finish.entity.AclUser;
import com.xiahan.shiro.finish.mapper.AclUserMapper;
import com.xiahan.shiro.finish.service.AclUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiahan.util.PageUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xiahan
 * @since 2021-03-07
 */
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements AclUserService {

  @Override
  public PageUtils queryPage(long current, long limit, Map<String, Object> params) {
      Page<AclUser> page = new Page<>(current,limit);
      baseMapper.selectPage(page, null);
      return new PageUtils(page);
   }
}
