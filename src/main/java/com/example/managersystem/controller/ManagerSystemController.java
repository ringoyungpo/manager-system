package com.example.managersystem.controller;

import com.alibaba.fastjson2.JSON;
import com.example.managersystem.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class ManagerSystemController {
    private final ManagerSystemService managerSystemService;

    public ManagerSystemController(ManagerSystemService managerSystemService) {
        this.managerSystemService = managerSystemService;
    }

    @RequestMapping("/admin/addUser")
    public ResponseEntity<String> addUser(@RequestBody AddUserCommand command,  @RequestHeader(name = "Authorization") String authorization){
        UserInfo userInfo = getUserInfo(authorization);
        try {
            this.managerSystemService.addUser(command, userInfo);
            return ResponseEntity.ok("Success");
        } catch (NoAccessException e) {
            return ResponseEntity.badRequest().body("No Access");
        }
    }

    @RequestMapping("/user/{resource}")
    public ResponseEntity<String> getResource(@PathVariable String resource,  @RequestHeader(name = "Authorization") String authorization){
        UserInfo userInfo = getUserInfo(authorization);
        try {
            return ResponseEntity.ok(this.managerSystemService.getResource(new GetResourceQuery(resource), userInfo));
        } catch (NoAccessException e) {
            return ResponseEntity.badRequest().body("No Access");
        }
    }


    private UserInfo getUserInfo(String authorization) {
        String decodedHeader = new String(Base64.getDecoder().decode(authorization));
        return JSON.parseObject(decodedHeader, UserInfo.class);
    }
}
