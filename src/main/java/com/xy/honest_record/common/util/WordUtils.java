package com.xy.honest_record.common.util;

import java.io.*;
import java.util.Map;
import java.util.UUID;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 *
 **/
@Component
public class WordUtils {
    private static final String FTL_FP = "static/doc/"; //ftl所在模板路径

    private static Configuration configuration = null;
    static{
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("utf-8");//设置默认的编码

    }

    public  Boolean writeWordReport(String wordFilePath,String wordFileName,String templateFileName, Map<String, Object> beanParams) {
        Writer out = null;
        try {
            configuration.setDirectoryForTemplateLoading(new ClassPathResource(FTL_FP).getFile());
            Template template = configuration.getTemplate(templateFileName, "UTF-8");//根据ftl模板文件获取模板

            //获取输出文件目录，如果不存在则创建
            String filePath = "";
            int index = wordFilePath.lastIndexOf(File.separator);
            if(index != wordFilePath.length()-1){
                filePath = wordFilePath+ File.separator;
            }else {
                filePath = wordFilePath;
            }
            File file1 = new File(filePath);
            if(!file1.exists()){
                file1.mkdirs();
            }

            //输出文件
            File file = new File(filePath+wordFileName);
            FileOutputStream fos = new FileOutputStream(file);
            out = new OutputStreamWriter(fos, "UTF-8");
            template.process(beanParams, out); //模板渲染数据
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 将base64格式字符串去除类型前缀 转换为  真正数据
     * 例如图片数据格式为data:image/png;base64,iVBORw0KGgoAA...  在"base64,"之后的才是图片信息
     */
    public static String turn(String str){
        String base64 = str.replaceAll(" ", "+");
        String[] arr = base64.split("base64,");
        String image = arr[1];
        return image;

    }

    public static void main(String[] args) throws Exception {

        //在springboot项目中，读写 resources 目录下文件的方式， 例如读写文件目录为 resources/static/doc
        //resources 为 ClassPath （类路径）；
        ClassPathResource classPathResource = new ClassPathResource("static/doc");
        //获取文件输入流，准备在内存中读写
        InputStream inputStream =classPathResource.getInputStream();


        System.out.println(classPathResource.getPath());// static/doc
        System.out.println(classPathResource.getURI().getPath());//  /C:/Users/27682/Desktop/honest_record/target/classes/static/doc
        System.out.println(classPathResource.getURL().getPath());//  /C:/Users/27682/Desktop/honest_record/target/classes/static/doc

        //列出  resources/static/doc 目录下的所有文件
        for (String s : classPathResource.getFile().list()) {
            System.out.println(s);
        }

        System.out.println(UUID.randomUUID().toString());

        System.out.println(RandomUtil.randomString(10));



    }

}
