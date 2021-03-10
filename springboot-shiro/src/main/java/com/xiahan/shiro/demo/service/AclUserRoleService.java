package com.xiahan.shiro.demo.service;

import com.xiahan.shiro.demo.entity.AclUserRole;
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
public interface AclUserRoleService extends IService<AclUserRole> {

    PageUtils queryPage(long current, long limit, Map<String, Object> params);
}
