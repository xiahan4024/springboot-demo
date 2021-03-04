package com.xiahan.security.demo.service;

import com.xiahan.security.demo.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiahan.util.PageUtils;

import java.util.Map;
/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xiahan
 * @since 2021-02-24
 */
public interface AclUserService extends IService<AclUser> {

    PageUtils queryPage(long current, long limit, Map<String, Object> params);

    AclUser loadUserByUsername(String username);
}
