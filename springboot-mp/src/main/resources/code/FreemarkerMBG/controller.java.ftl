package ${package.Controller};

import com.xiahan.util.Result;
import com.xiahan.util.PageUtils;
import ${package.Entity}.${entity};
import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import java.util.Map;
import java.util.Arrays;

/**
 * <p>
 * ${table.comment!} xiahan
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@Api(tags = {"${table.comment!}"})
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    ${table.serviceName} ${cfg.servicename};

    /**
    * 列表
    */
    @GetMapping("/list/{current}/{limit}")
    @ApiOperation(value = "查询分页数据")
    public Result list(@PathVariable long current, @PathVariable long limit, @RequestParam Map<String, Object> params){
        PageUtils page = ${cfg.servicename}.queryPage(current, limit, params);
        return Result.ok().data("page", page);
    }

    @RequestMapping("/info/{id}")
    @ApiOperation(value = "根据id查询数据")
    public Result info(@PathVariable("id") Long id){
        ${entity} wareInfo = ${cfg.servicename}.getById(id);
        return Result.ok().data("wareInfo", wareInfo);
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增数据")
    public Result save(@RequestBody ${entity} wareInfo){
        ${cfg.servicename}.save(wareInfo);
        return Result.ok();
    }

    @RequestMapping("/update")
    @ApiOperation(value = "更新数据")
    public Result update(@RequestBody ${entity} wareInfo){
        ${cfg.servicename}.updateById(wareInfo);
        return Result.ok();
    }

    @RequestMapping("/delete")
    @ApiOperation(value = "删除数据")
    public Result delete(@RequestBody Long[] ids){
        ${cfg.servicename}.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
</#if>
