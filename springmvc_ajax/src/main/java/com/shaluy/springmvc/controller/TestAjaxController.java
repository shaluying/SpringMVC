package com.shaluy.springmvc.controller;

import com.shaluy.springmvc.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1、@RequestBody：将请求体中的内容和控制器方法的形参进行绑定
 * 2、使用@RequestBody注解将json格式的请求参数转换为java对象
 * a>导入jackson的依赖
 * b>在SpringMVC的配置文件中设置<mvc:annotation-driven />
 * c>在处理请求的控制器方法的形参位置，直接设置json格式的请求参数要转换的java类型的形参，使用@RequestBody注解标识即可
 * 3、@ResponseBody：将所标识的控制器方法的返回值作为响应报文的响应体响应到浏览器
 * 4、使用@ResponseBody注解响应浏览器json格式的数据
 * a>导入jackson的依赖
 * b>在SpringMVC的配置文件中设置<mvc:annotation-driven />
 * c>将需要转换为json字符串的java对象直接作为控制器方法的返回值，使用@ResponseBody注解标识控制器方法
 * 就可以将java对象直接转换为json字符串，并响应到浏览器
 *
 * 常用的Java对象转换为json的结果：
 * 实体类-->json对象
 * map-->json对象
 * list-->json数组
 */

@Controller
//@RestController //表示此类所有的控制器方法都有@Controller+@ResponseBody注解
public class TestAjaxController {

    @RequestMapping("/test/ajax")
    public void testAjax(Integer id, HttpServletResponse response) throws IOException {
        System.out.println("id = " + id);
        response.getWriter().write("hello,ajax");
    }

//    @RequestMapping("/test/RequestBody/json")
    public void testRequestBodyJson(Integer id, @RequestBody User user, HttpServletResponse response) throws IOException {
        System.out.println("id = " + id);
        System.out.println("user = " + user);
        response.getWriter().write("hello,RequestBody");
    }

    @RequestMapping("/test/RequestBody/json")
    public void testRequestBodyJson(Integer id, @RequestBody Map<String, Object> map, HttpServletResponse response) throws IOException {
        System.out.println("id = " + id);
        System.out.println("map = " + map);
        response.getWriter().write("hello,RequestBody");
    }

    @RequestMapping("/test/ResponseBody")
    @ResponseBody
    public String testResponseBody(){
        return "success";
    }

//    @RequestMapping("/test/ResponseBody/json")
    @ResponseBody
    public User testResponseBodyJson(Integer id, @RequestBody User user){
        System.out.println("id = " + id);
        System.out.println("user = " + user);

        User userResp = new User(1001,"张三","123456",22,"男");
        return userResp;
    }

//    @RequestMapping("/test/ResponseBody/json")
//    @ResponseBody
//    public Map testResponseBodyJson(Integer id, @RequestBody Map<String, Object> map){
//        System.out.println("id = " + id);
//        System.out.println("map = " + map);
//
//        User userResp1 = new User(1001,"张一","123456",22,"男");
//        User userResp2 = new User(1002,"张二","123456",22,"男");
//        User userResp3 = new User(1003,"张三","123456",22,"男");
//        Map<String, Object> mapResp = new HashMap<>();
//        mapResp.put("1001",userResp1);
//        mapResp.put("1002",userResp2);
//        mapResp.put("1003",userResp3);
//        return mapResp;
//    }

    @RequestMapping("/test/ResponseBody/json")
    @ResponseBody
    public List testResponseBodyJson(Integer id, @RequestBody Map<String, Object> map){
        System.out.println("id = " + id);
        System.out.println("map = " + map);

        User userResp1 = new User(1001,"张一","123456",22,"男");
        User userResp2 = new User(1002,"张二","123456",22,"男");
        User userResp3 = new User(1003,"张三","123456",22,"男");
        List<User> listResp = Arrays.asList(userResp1, userResp2, userResp3);
        return listResp;
    }

}
