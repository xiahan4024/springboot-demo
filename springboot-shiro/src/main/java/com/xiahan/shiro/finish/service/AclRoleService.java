package com.xiahan.shiro.finish.service;

import com.xiahan.shiro.finish.entity.AclRole;
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
