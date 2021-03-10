package com.xiahan.shiro.demo.controller;

import com.xiahan.util.Result;
import com.xiahan.util.PageUtils;
import com.xiahan.shiro.demo.entity.AclRole;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xiahan.shiro.demo.service.AclRoleService;
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
 * @since 2021-03-07
 */
@Api(tags = {""})
@RestController
@RequestMapping("/acl-role")
public class AclRoleController {

    @Autowired
    AclRoleService baseService;

    @GetMapping("/list/{current}/{limit}")
    @ApiOperation(value = "查询分页数据")
    public Result list(@PathVariable long current, @PathVariable long limit, @RequestParam Map<String, Object> params){
        PageUtils page = baseService.queryPage(current, limit, params);
        return Result.ok().data("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据id查询数据")
    public Result info(@PathVariable("id") String id){
        AclRole baseEntity = baseService.getById(id);
        return Result.ok().data("baseEntity", baseEntity);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增数据")
    public Result save(@RequestBody AclRole baseEntity){
        baseService.save(baseEntity);
        return Result.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新数据")
    public Result update(@RequestBody AclRole baseEntity){
        baseService.updateById(baseEntity);
        return Result.ok();
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除数据")
    public Result delete(@RequestBody String[] ids){
        baseService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
