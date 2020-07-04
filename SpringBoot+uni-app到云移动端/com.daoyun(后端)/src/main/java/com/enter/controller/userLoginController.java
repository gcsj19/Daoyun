package com.enter.controller;

import com.enter.entity.LoginBody;
import com.enter.entity.User;
import com.enter.entity.UserLogin;
import com.enter.entity.UserRole;
import com.enter.service.RoleService;
import com.enter.service.UserRoleService;
import com.enter.service.UserService;
import com.enter.service.userLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@CrossOrigin
@RestController
@Slf4j
public class userLoginController {

    @Autowired
    userLoginService service;

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;

    @ResponseBody
    @GetMapping("/userLogin")
    @CrossOrigin
    public String isPermission(
             @RequestParam("id") String id,
             @RequestParam("userPassword") String userpassword,
             HttpServletRequest request){

        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(id);
        userLogin.setUserPassword(userpassword);

        // 登录认证，认证成功后将用户信息放到session中
        boolean auth = service.isPermissonLogin(userLogin);
        String token = service.getToken(userLogin);
        System.out.println(token);
        if(auth){
//            request.getSession().setAttribute("userInfo", id + " - " + userpassword);
//           System.out.println(request.getSession().getAttribute("userInfo"));
           // response.getWriter().write("userInfo");
            //response.addCookie(new Cookie("token",token));
            return token;

        }
        else {
            return null;

        }


    }

    @RequestMapping("/userSignUp")
    @ResponseBody
    public boolean UserSignUp(@RequestParam("userId") String id,
                           @RequestParam("userPassword") String password,
                           @RequestParam("userType")String usertype){


        if(id == "" || password == ""){
            return false;
        }
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(id);
        userLogin.setUserPassword(password);
        userLogin.setUserType(usertype);


        String token = UUID.randomUUID().toString();
        userLogin.setCertificate(token);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String str = df.format(new Date());
        Date date = new Date();
        try {

            date = df.parse(str);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        int roleId = roleService.getRoleId(usertype);
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRole.setRoleId(roleId);
        userRole.setCreateTime(date);
        userRole.setModifyTime(date);

        User user = new User();
        user.setPhone(id);
        user.setCreateTime(date);
        user.setModifyTime(date);

        try {
            boolean isWork = service.insertUserLogin(userLogin);

            if(isWork){
                userService.signUpUser(user);
                userRoleService.insert(userRole);
            }
            else {
                return false;
            }

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @GetMapping(value = "/loginout")
    public String loginout(HttpServletRequest request) {
        String info = "登出操作";
        log.info(info);
        HttpSession session = request.getSession();

        // 将用户信息从session中删除
        session.removeAttribute("userInfo");

        Object userInfo = session.getAttribute("userInfo");
        if (userInfo == null) {
            info = "登出成功";
        } else {
            info = "登出失败";
        }
        log.info(info);
        return info;

    }



}
