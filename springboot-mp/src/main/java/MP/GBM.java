package MP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * 	mybatis-plus 逆向工程
 * @author xiahan
 * @dateTime 2019年10月27日 下午9:47:58
 */
public class GBM {
	
	public static final String PACKAGEPARENT = "E:/JAVA/Windows/STSWorkspace/springboot-demo/springboot-mp";
	public static final String PROJECTPATH = "E:/JAVA/Windows/STSWorkspace/springboot-demo/springboot-mp";
	public static final String OUTPUTDIR = "/src/main/java";
    public static final String AUTHOR = "xiahan";
    public static final String DBURL = "jdbc:mysql://192.168.110.100:3306/mp_test?characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Taipei";
    public static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
//    public static final String DBDRIVER = "com.mysql.jdbc.Driver";
    public static final String DBUSER = "root";
    public static final String DBPASSWORD = "xiahan";


    public static void main_freemarker(String[] args) {

    	// 1.全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(PROJECTPATH + OUTPUTDIR) // 生成路径
	        .setAuthor(AUTHOR) // 作者
	        .setOpen(true) // 完成打开
	        .setFileOverride(true) //是否覆盖文件
	        .setMapperName("%sMapper") //自定义文件名
	        .setServiceName("%sService") //不定义接口会  IXXXService
	        .setServiceImplName("%sServiceImpl")
//	        .setIdType(IdType.AUTO) // 主键策略
	        .setBaseResultMap(true)  //base map
	        .setBaseColumnList(true) //columnlist
//	        .setActiveRecord(true) // 是否支持AR模式
	        ;
        

        // 2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
        		.setUrl(DBURL)
		        .setDriverName(DBDRIVER)
		        .setUsername(DBUSER)
		        .setPassword(DBPASSWORD)
		        ;

        // 3.策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
	        .setColumnNaming(NamingStrategy.underline_to_camel)  // 指定表名 字段名是否使用下划线
	        .setEntityLombokModel(true)  //是否使用 lombok
	        .setRestControllerStyle(true) //是否使用rest
	        .setCapitalMode(true) //全局大写命名
	        .setTablePrefix("tb_") //设置表名前缀
	        .setInclude("tb_student")  // 生成的表
//	        .setExclude("") //不逆向生成的表
	        ;
        
        // 写于父类中的公共字段
//        strategyConfig.setSuperEntityColumns("id")
//	        .setInclude(scanner("表名"))
//	        .setControllerMappingHyphenStyle(true)
//	        .setTablePrefix(pc.getModuleName() + "_")
//	        ;
        
        
        // 4.包配置
        PackageConfig packageConfig = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        packageConfig.setParent(PACKAGEPARENT)
		        .setMapper("mapper")
				.setService("service")
				.setServiceImpl("serviceImpl")
				.setController("controller")
				.setEntity("beans")
//				.setXml("mapper") //使用这个会将xml与java放在一起
		        ;

        // 5.自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return PROJECTPATH + "/src/main/resources/mybatis/mapper/" //+ pc.getModuleName() + "/"
                         + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        injectionConfig.setFileOutConfigList(focList);
        

        // 6.配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setXml(null);

        
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(globalConfig)
        	.setDataSource(dataSourceConfig)
        	.setStrategy(strategyConfig)
        	.setPackageInfo(packageConfig)
        	.setCfg(injectionConfig)
//        	.setTemplate(templateConfig)
        	.setTemplateEngine(new FreemarkerTemplateEngine())
        	;
        
        generator.execute();
    }
    
    public static void main(String[] args) {

    	// 1.全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(PROJECTPATH + OUTPUTDIR) // 生成路径
	        .setAuthor(AUTHOR) // 作者
	        .setOpen(true) // 完成打开
	        .setFileOverride(true) //是否覆盖文件
	        .setMapperName("%sMapper") //自定义文件名
	        .setServiceName("%sService") //不定义接口会  IXXXService
	        .setServiceImplName("%sServiceImpl")
//	        .setIdType(IdType.AUTO) // 主键策略
	        .setBaseResultMap(true)  //base map
	        .setBaseColumnList(true) //columnlist
//	        .setActiveRecord(true) // 是否支持AR模式
	        ;
        

        // 2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
        		.setUrl(DBURL)
		        .setDriverName(DBDRIVER)
		        .setUsername(DBUSER)
		        .setPassword(DBPASSWORD)
		        ;

        // 3.策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
	        .setColumnNaming(NamingStrategy.underline_to_camel)  // 指定表名 字段名是否使用下划线
	        .setEntityLombokModel(true)  //是否使用 lombok
	        .setRestControllerStyle(true) //是否使用rest
	        .setCapitalMode(true) //全局大写命名
	        .setTablePrefix("tb_") //设置表名前缀
	        .setInclude("tb_student")  // 生成的表
//	        .setExclude("") //不逆向生成的表
	        ;
        
        // 写于父类中的公共字段
//        strategyConfig.setSuperEntityColumns("id")
//	        .setInclude(scanner("表名"))
//	        .setControllerMappingHyphenStyle(true)
//	        .setTablePrefix(pc.getModuleName() + "_")
//	        ;
        
        
        // 4.包配置
        PackageConfig packageConfig = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        packageConfig.setParent(PACKAGEPARENT)
		        .setMapper("mapper")
				.setService("service")
				.setServiceImpl("serviceImpl")
				.setController("controller")
				.setEntity("beans")
//				.setXml("mapper") //使用这个会将xml与java放在一起
		        ;

        // 5.自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return PROJECTPATH + "/src/main/resources/mybatis/mapper/" //+ pc.getModuleName() + "/"
                         + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        injectionConfig.setFileOutConfigList(focList);
        

        // 6.配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setXml(null);

        
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(globalConfig)
        	.setDataSource(dataSourceConfig)
        	.setStrategy(strategyConfig)
        	.setPackageInfo(packageConfig)
        	.setCfg(injectionConfig)
//        	.setTemplate(templateConfig)
//        	.setTemplateEngine(new FreemarkerTemplateEngine())  //freemarker必须添加
        	;
        
        generator.execute();
    }

}
