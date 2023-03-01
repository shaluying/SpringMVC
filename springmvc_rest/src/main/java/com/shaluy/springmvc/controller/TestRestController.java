package com.shaluy.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 查询所有的用户信息-->/user-->get
 * 根据id查询用户信息-->/user/1-->get
 * 新增用户信息-->/user-->post
 * 修改用户信息-->/user-->put
 * 根据id删除用户信息-->/user/1-->delete
 */

@Controller
public class TestRestController {

    //    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping(value = "/user")
    public String getAllUser() {
        System.out.println("查询所有的用户信息-->/user-->get");
        return "success";
    }

    //    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @GetMapping(value = "/user/{id}")
    public String getUserById(@PathVariable("id") Integer id) {
        System.out.println("查询所有的用户信息-->/user/" + id + "-->get");
        return "success";
    }

    //    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @PostMapping(value = "/user")
    public String insertUser() {
        System.out.println("新增用户信息-->/user-->post");
        return "success";
    }

//    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @PutMapping(value = "/user")
    public String updateUser() {
        System.out.println("修改用户信息-->/user-->put");
        return "success";
    }

//    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @DeleteMapping(value = "/user/{id}")
    public String deleteUserById(@PathVariable("id") Integer id) {
        System.out.println("根据id删除用户信息-->/user/" + id + "-->delete");
        return "success";
    }

}
