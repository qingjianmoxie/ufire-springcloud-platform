package com.github.ufire.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ufire-springcloud-platform
 * @description: X
 * @author: fengandong
 * @create: 2020-12-28 15:33
 **/
@RestController
@RefreshScope
@RequestMapping(value = "/hello")
public class HelloController {
    @Value("${app.version:''}")
    private String version;
    @RequestMapping(value = "/info")
    public String sayHello(){
        return "version:"+version;
    }
}
