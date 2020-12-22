package com.xy.honest_record;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

public class MPCodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir"); //当前项目根路径
        gc.setOutputDir(projectPath + "/src/main/java");  //生成代码的输出目录
        gc.setAuthor("xuyong"); //注释中作者名称
        gc.setOpen(false);      //代码生成完毕之后是否自动打开资源管理器
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        gc.setFileOverride(false); //新生成的代码是否覆盖旧的代码
        gc.setServiceName("I%sService"); //业务层生成的接口名称为 IxxxService
        gc.setServiceImplName("%sService");//设置生成的Service实现类名称为 xxxService
        gc.setIdType(IdType.AUTO);//在主键上添加@TableId指定主键自动生成策略为自增
        gc.setDateType(DateType.ONLY_DATE);//数据库时间类型 到 实体类时间类型 对应策略
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/honest_record?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("xy");
        dsc.setPassword("xy123456");
        dsc.setDbType(DbType.MYSQL);//
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.xy.honest_record"); //父包
        //pc.setModuleName("blog"); //当前生成的代码全部放在 com.xy.mybatisplus_demo.blog.xxx 下面，这里可以不设置，直接使用父包
        pc.setEntity("entity"); //实体类 包名
        pc.setMapper("mapper"); //数据访问层 包名
        pc.setService("service");//业务层 包名
        pc.setController("controller");//控制器 包名
        mpg.setPackageInfo(pc);


        // 生成策略配置

        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(
                "accusation",
                "accuse_type",
                "decision_paper",
                "department",
                "faculty",
                "log",
                "notice",
                "power",
                "problem_field",
                "problem_type",
                "role",
                "role_power"
        ); //要自动生成代码的表，多个表以逗号分隔

        strategy.setNaming(NamingStrategy.underline_to_camel);  //表名如果是下划线生成实体时转驼峰，table_name转tableName
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//表的列名如果是下划线生成实体属性时转驼峰，column_name转columnName

        strategy.setEntityLombokModel(true); //为实体添加 lombok 主键，如@Data
        strategy.setRestControllerStyle(true); //控制器注解由@Controller 改为 @RestController

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine()); //设置生成代码模板引擎，默认为Velocity，在pom中需要导入依赖
        mpg.execute();
    }

}
