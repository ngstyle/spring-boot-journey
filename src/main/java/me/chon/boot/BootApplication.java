package me.chon.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动方式
 * 1. 启动当前类 main 方法
 * 2. 在项目根目录下运行 mvn spring-boot:run
 * 3. 在项目根目录下运行 mvn install命令生成jar 再运行target目录下的jar
 *    可以加参数 --spring.profiles.active=dev    加载配置文件不一样
 */
@SpringBootApplication
@MapperScan("me.chon.boot.dao")
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
