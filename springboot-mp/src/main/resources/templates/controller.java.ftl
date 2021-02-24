package ${package.Controller};

import com.xiahan.util.Result;
import com.xiahan.util.PageUtils;
import ${package.Entity}.${entity};
import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

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
    public Result list(@PathVariable long current, @PathVariable long limit, @RequestParam Map<String, Object> params){
    PageUtils page = ${cfg.servicename}.queryPage(current, limit, params);

    return Result.ok().data("page", page);
    }


    /**
    * 信息
    */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id){
    ${entity} wareInfo = ${cfg.servicename}.getById(id);

    return Result.ok().data("wareInfo", wareInfo);
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    public Result save(@RequestBody ${entity} wareInfo){
    ${cfg.servicename}.save(wareInfo);
    return Result.ok();
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public Result update(@RequestBody ${entity} wareInfo){
    ${cfg.servicename}.updateById(wareInfo);

    return Result.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public Result delete(@RequestBody Long[] ids){
    ${cfg.servicename}.removeByIds(Arrays.asList(ids));

    return Result.ok();
    }

}
</#if>
