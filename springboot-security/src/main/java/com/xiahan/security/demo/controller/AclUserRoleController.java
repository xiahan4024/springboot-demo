package com.xiahan.security.demo.controller;

import com.xiahan.security.demo.entity.AclUserRole;
import com.xiahan.util.PageUtils;
import com.xiahan.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xiahan.security.demo.service.AclUserRoleService;
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
 *  xiahan
 * </p>
 *
 * @author xiahan
 * @since 2021-02-24
 */
@Api(tags = {"用户-角色 关联关系"})
@RestController
@RequestMapping("/userrole")
public class AclUserRoleController {

    @Autowired
    AclUserRoleService aclPermissionService;

    /**
    * 列表
    */
    @GetMapping("/list/{current}/{limit}")
    @ApiOperation(value = "查询分页数据")
    public Result list(@PathVariable long current, @PathVariable long limit, @RequestParam Map<String, Object> params){
        PageUtils page = aclPermissionService.queryPage(current, limit, params);
        return Result.ok().data("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据id查询数据")
    public Result info(@PathVariable("id") String id){
        AclUserRole wareInfo = aclPermissionService.getById(id);
        return Result.ok().data("wareInfo", wareInfo);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增数据")
    public Result save(@RequestBody AclUserRole wareInfo){
        aclPermissionService.save(wareInfo);
        return Result.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新数据")
    public Result update(@RequestBody AclUserRole wareInfo){
        aclPermissionService.updateById(wareInfo);
        return Result.ok();
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除数据")
    public Result delete(@RequestBody Long[] ids){
        aclPermissionService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
