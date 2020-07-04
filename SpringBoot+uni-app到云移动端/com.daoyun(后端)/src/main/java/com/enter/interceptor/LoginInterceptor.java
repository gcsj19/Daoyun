package com.enter.interceptor;

import com.enter.service.userLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    userLoginService us;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String requesetToken = request.getParameter( "token" );
        if(requesetToken == null)
        {
            response.getWriter().write("Access Invalid");
            return false;
        }
        else {

            boolean isPermission = us.isPermissionOptions(requesetToken);
            if(isPermission){
                return true;
            }
            else {
                response.getWriter().write("Access Invalid");
                return false;
            }

        }

        //HttpSession session = request.getSession();

        // 获取用户信息，如果没有用户信息直接返回提示信息
//       Object userInfo = session.getAttribute("userInfo");
//        if (userInfo == null) {
//
//            response.getWriter().write("Please Login In");
//            return false;
//        } else {
//            System.out.println("当前会话"+ session.getAttribute("userInfo"));
//        }
//
//        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}