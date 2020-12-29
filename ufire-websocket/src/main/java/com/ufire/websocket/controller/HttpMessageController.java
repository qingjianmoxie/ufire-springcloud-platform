package com.ufire.websocket.controller;

import com.ufire.websocket.server.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ufire-springcloud-platform
 * @description: XX
 * @author: fengandong
 * @create: 2020-12-29 15:48
 **/
@RestController
@RequestMapping(value = "/websocket")
public class HttpMessageController {
    @Autowired
    private MyWebSocket myWebSocket;

    @RequestMapping(value = "to/{userId}/{message}")
    public String sendMessage(@PathVariable String userId, @PathVariable String message) {
        myWebSocket.sendInfo(userId, message);
        return "OK";
    }
}

