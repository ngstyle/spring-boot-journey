package me.chon.boot.controller;

import org.springframework.web.bind.annotation.*;


/**
 * 可以用 @Value 加载配置文件中的内容
 * 或者 @Compnent + @ConfigurationProperties 在配置文件配置实体类
 *
 * @RequestMapping 映射匹配，加载类上面的话，访问路径要添加上
 */
@RestController
public class HelloController {


//    @RequestMapping(value = {"/","/hello"}, method =  RequestMethod.GET)
    @RequestMapping("/hello")
    public String hello() {
        return "Hello, Spring!" ;
    }


    @GetMapping("/name/{name}")
    public String helloxxx(@PathVariable("name") String name) {
        return "Hello, Spring Boot PathVariable: " + name;
    }

//    http://localhost:8080/xx?name=nohc
//    或者post请求
    @GetMapping("/xx")
    public String helloxxx2(@RequestParam(value = "name", defaultValue = "20") String name) {
        return "Hello, Spring Boot RequestParam: " + name;
    }


}
