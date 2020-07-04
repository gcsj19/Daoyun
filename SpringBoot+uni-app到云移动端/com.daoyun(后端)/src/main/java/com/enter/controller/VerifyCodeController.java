package com.enter.controller;

import com.enter.entity.VerifyCode;
import com.enter.util.IVerifyCodeGen;
import com.enter.util.Impl.SimpleCharVerifyCodeGenImpl;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;




@RestController
@CrossOrigin
public class VerifyCodeController {

    private String rightCode = null;

    @ApiOperation(value = "验证码")
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            this.rightCode = code;
           // LOGGER.info(code);
            //将VerifyCode绑定session
            //request.getSession().setAttribute("VerifyCode", code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
           // LOGGER.info((Supplier<String>) e);

        }

    }

    @ResponseBody
    @GetMapping("/isVerified")
    @CrossOrigin
    public boolean isVerified(@RequestParam("code") String code){

        if(code.toUpperCase().equals(this.rightCode.toUpperCase())){

            return true;
        }
        return false;
    }

}
