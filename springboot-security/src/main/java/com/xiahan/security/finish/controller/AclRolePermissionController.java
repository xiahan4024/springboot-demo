package com.xiahan.security.finish.controller;

import com.xiahan.util.Result;
import com.xiahan.util.PageUtils;
import com.xiahan.security.finish.entity.AclRolePermission;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xiahan.security.finish.service.AclRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Arrays;

/**
 * <p>
 * 角色权限 xiahan
 * </p>
 *
 * @author xiahan
 * @since 2021-03-01
 */
@Api(tags = {"角色权限"})
@RestController
@RequestMapping("/acl-role-permission")
public class AclRolePermissionController {

    @Autowired
    AclRolePermissionService persistentLoginsService;

    @GetMapping("/list/{current}/{limit}")
    @ApiOperation(value = "查询分页数据")
    public Result list(@PathVariable long current, @PathVariable long limit, @RequestParam Map<String, Object> params){
        PageUtils page = persistentLoginsService.queryPage(current, limit, params);
        return Result.ok().data("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据id查询数据")
    public Result info(@PathVariable("id") String id){
        AclRolePermission persistentLogins = persistentLoginsService.getById(id);
        return Result.ok().data("persistentLogins", persistentLogins);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增数据")
    public Result save(@RequestBody AclRolePermission persistentLogins){
        persistentLoginsService.save(persistentLogins);
        return Result.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新数据")
    public Result update(@RequestBody AclRolePermission persistentLogins){
        persistentLoginsService.updateById(persistentLogins);
        return Result.ok();
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除数据")
    public Result delete(@RequestBody Long[] ids){
        persistentLoginsService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
