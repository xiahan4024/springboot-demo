package com.xiahan.shiro.demo.service;

import com.xiahan.shiro.demo.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiahan.util.PageUtils;
import java.util.Map;
/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xiahan
 * @since 2021-03-07
 */
public interface AclUserService extends IService<AclUser> {

    PageUtils queryPage(long current, long limit, Map<String, Object> params);
}
