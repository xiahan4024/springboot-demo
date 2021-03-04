package com.xiahan.security.finish.service;

import com.xiahan.security.finish.entity.AclUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiahan.util.PageUtils;
import java.util.Map;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiahan
 * @since 2021-03-01
 */
public interface AclUserRoleService extends IService<AclUserRole> {

    PageUtils queryPage(long current, long limit, Map<String, Object> params);
}
