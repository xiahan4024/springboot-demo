package com.xiahan.security.demo.service;

import com.xiahan.security.demo.entity.AclUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiahan.util.PageUtils;

import java.util.Map;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiahan
 * @since 2021-02-24
 */
public interface AclUserRoleService extends IService<AclUserRole> {

    PageUtils queryPage(long current, long limit, Map<String, Object> params);
}
