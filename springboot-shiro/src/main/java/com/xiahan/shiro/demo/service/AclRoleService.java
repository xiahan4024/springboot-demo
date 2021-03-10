package com.xiahan.shiro.demo.service;

import com.xiahan.shiro.demo.entity.AclRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiahan.util.PageUtils;
import java.util.Map;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiahan
 * @since 2021-03-07
 */
public interface AclRoleService extends IService<AclRole> {

    PageUtils queryPage(long current, long limit, Map<String, Object> params);
}
