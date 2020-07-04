package com.enter.controller;

import com.enter.entity.User;
import com.enter.entity.UserLogin;
import com.enter.entity.UserRequestBody;
import com.enter.service.UserService;
import com.enter.service.userLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    userLoginService us;


    @ResponseBody
    @GetMapping("/userInfo")
    @CrossOrigin
    public UserRequestBody getUser(@RequestParam("id") String id,
                                   @RequestParam("token") String token
                                    ){
        int tableId = userService.getSelectedId(id);
        User user = userService.getUserInfo(tableId);
        String certificate = us.getToken(id);
        String userType = us.getUserType(id);



        if(user == null){
            return null;
        }
        if(certificate.equals(token)){
            UserRequestBody userRequestBody = new UserRequestBody();
            userRequestBody.setPhone(user.getPhone());
            userRequestBody.setEmail(user.getEmail());
            userRequestBody.setUserName(user.getUserName());
            userRequestBody.setMajor(user.getMajor());
            userRequestBody.setUserType(userType);

            return userRequestBody;
        }
        else {
            return null;
        }

    }
    @GetMapping("/updateUserName")
    public boolean updateName(@RequestParam("userName") String name,
                              @RequestParam("id") String id,
                              @RequestParam("token") String token){

        if(us.isPermissionOptions(token)){
            boolean isWork = userService.userNameUpdate(name,id);
            return  isWork;
        }
        else {
            return false;
        }

    }
    @GetMapping("/user")
    public String getHello(HttpServletRequest request){
        System.out.println(request.getParameter("id"));
        return "hello";
    }



}
