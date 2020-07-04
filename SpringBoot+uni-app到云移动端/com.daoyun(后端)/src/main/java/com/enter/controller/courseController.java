package com.enter.controller;


import com.enter.entity.*;
import com.enter.service.CourseService;
import com.enter.service.SignService;
import com.enter.service.UserService;
import com.enter.service.userLoginService;
import com.enter.util.LocationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class courseController {

    @Autowired
    CourseService courseService;
    @Autowired
    userLoginService us;
    @Autowired
    SignService signService;
    @Autowired
    UserService userService;

    @GetMapping("/courseInfo")
    public List<Course> getCourseInfo(@RequestParam("id") String id,
                                      HttpServletRequest request){


        String requesetToken = request.getParameter( "token" );
        boolean isPermission = us.isPermissionOptions(requesetToken);
        if(isPermission){
            List<Course> courses = courseService.getCourse(id);
            return courses;
        }
        else {
            return null;
        }

    }

    @GetMapping("/sign")
    public boolean addSign(@RequestParam("courseName") String name,
                           @RequestParam("userId") String userId,
                           @RequestParam("longitude") String longitude,
                           @RequestParam("latitude") String latitude,
                           @RequestParam("time") String time){


        Sign sign = new Sign();
        int courseId = courseService.getId(userId,name);
        sign.setCourseId(courseId);
        sign.setLongitude(Double.parseDouble(longitude));
        sign.setLatitude(Double.parseDouble(latitude));
        sign.setUserId(userId);
        Timestamp statTime = Timestamp.valueOf(time);
        Timestamp endTime = new Timestamp(statTime.getTime()+ 300000);
        sign.setCreateTime(statTime);
        sign.setEndTime(endTime);
        sign.setAlive(true);


        boolean isWork = signService.insertSign(sign);
        if(isWork)
            return true;
        else
            return false;

    }

    @GetMapping("/userSign")
    public SignRequestBody addUserSign(@RequestParam("courseId")int id,
                               @RequestParam("userId") String userId,
                               @RequestParam("longitude") String longitude,
                               @RequestParam("latitude") String latitude,
                               @RequestParam("time")String time){


        SignRequestBody signRequestBody = new SignRequestBody();

        UserSign userSign = new UserSign();
        int signId = signService.getSignId(id);
        if(signId == -1){
            signRequestBody.setAlive(false);
            return  signRequestBody;
        }
        Sign sign = signService.getSign(signId);
        Timestamp createTime = Timestamp.valueOf(time);

        if(createTime.before(sign.getEndTime())){
            userSign.setLatitude(Double.parseDouble(latitude));
            userSign.setLongitude(Double.parseDouble(longitude));
            userSign.setSignId( signId );
            userSign.setUserId( userId );
            userSign.setCreateTime(createTime);
            boolean isWork = signService.insertUserSign( userSign );
            if(isWork){
                signRequestBody.setAlive(true);
            }else{
                signRequestBody.setAlive(false);
            }
        }else{
            signRequestBody.setAlive(false);
        }
        signRequestBody.setCourseId(sign.getCourseId());

        return signRequestBody;

    }

    @GetMapping("/checkSign")
    public SignRequestBody stopSign(@RequestParam("id")String id,
                            @RequestParam("time")String time){

        SignRequestBody signRequestBody = new SignRequestBody();
        Timestamp currentTime = Timestamp.valueOf(time);

        int signId = signService.getSignId(id);
        if(signId == -1){
            signRequestBody.setAlive( false );
            return signRequestBody;
        }

        Sign sign = signService.getSign(signId);
        if(sign == null){
            signRequestBody.setAlive( false );
            return signRequestBody;
        }

        if(sign.getEndTime().before(currentTime)){
            sign.setAlive(false);
            signRequestBody.setAlive(false);
            signService.updateSign( sign );
        }
        else{
            signRequestBody.setAlive(true);
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = "";
        try{
            endTime = sdf.format(sign.getEndTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        signRequestBody.setEndTime(endTime);
        signRequestBody.setCourseId(sign.getCourseId());
        return signRequestBody;

    }

    @GetMapping("/checkSign2")
    public SignRequestBody checkSign(@RequestParam("id")int id,
                                     @RequestParam("userId")String userId,
                                     @RequestParam("time")String time){
        SignRequestBody signRequestBody = new SignRequestBody();
        Timestamp currentTime = Timestamp.valueOf(time);
        int signId = signService.getSignId(id);
        if(signId == -1){
            signRequestBody.setAlive( false );
            return signRequestBody;
        }
        Sign sign = signService.getSign(signId);
        if(sign == null){
            signRequestBody.setAlive( false );
            return signRequestBody;
        }
        int userSignId = signService.getUserSignId(signId,userId);
        if(userSignId == -1){
            if(sign.getEndTime().before(currentTime)){
                signRequestBody.setAlive(false);
            }
            else{
                signRequestBody.setAlive(true);
            }
        }
        else {

            signRequestBody.setAlive(false);
            signRequestBody.setCourseId(-1);

        }
        return  signRequestBody;

    }


    @GetMapping("/courseMember")
    public List<MemberRequestBody> getMember(@RequestParam("courseId")int course_id){

        List<MemberRequestBody> memberRequestBodies = new ArrayList<MemberRequestBody>();
        List<CourseMember> courseMembers = courseService.getMember(course_id);

        for(int i = 0 ;i < courseMembers.size();i++){
            CourseMember courseMember = courseMembers.get(i);
            MemberRequestBody memberRequestBody = new MemberRequestBody();
            memberRequestBody.setUserId(courseMember.getUserId());
            int id = userService.getSelectedId(courseMember.getUserId());
            memberRequestBody.setUserName(userService.getUserInfo(id).getUserName());
            memberRequestBodies.add(memberRequestBody);
        }

        return memberRequestBodies;
    }
    @GetMapping("/courseInfo2")
    public List<CourseRequestBody> getCourseInfo2(@RequestParam("id")String id){

        List<CourseRequestBody> courseRequestBodies = new ArrayList<CourseRequestBody>();
        List<CourseMember> courseMembers = courseService.getMember(id);
        for(int i = 0; i < courseMembers.size();i++){
            CourseMember courseMember = courseMembers.get(i);
            CourseRequestBody courseRequestBody = new CourseRequestBody();
            int courseId = courseMember.getCourseId();
            courseRequestBody.setId(courseId);
            courseRequestBody.setName(courseService.getCourse(courseId).getName());
            courseRequestBodies.add(courseRequestBody);
        }
        return courseRequestBodies;

    }
    @GetMapping("/signRecord")
    public List<SignRecordBody> getSignRcord(@RequestParam("userId")String id,
                                             @RequestParam("courseId")int courseId
                                             ){
        List<SignRecordBody> signRecordBodies = new ArrayList<>();
        List<Sign> signs = signService.getSignList(id,courseId);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i = 0; i < signs.size();i++){
            Sign sign = signs.get(i);
            SignRecordBody signRecordBody = new SignRecordBody();
            signRecordBody.setSignId(sign.getId());
            signRecordBody.setCourseId(sign.getCourseId());
            String createTime = "";
            try{
                createTime = sdf.format(sign.getCreateTime());
            }catch (Exception e){
                e.printStackTrace();
            }
            signRecordBody.setCreateTime(createTime);
            signRecordBody.setLongitude(String.valueOf(sign.getLongitude()));
            signRecordBody.setLatitude(String.valueOf(sign.getLatitude()));
            signRecordBody.setAlive(sign.isAlive());
            signRecordBodies.add(signRecordBody);

        }
        return signRecordBodies;
    }
    @GetMapping("/signRecord2")
    public List<SignRecordBody> getStudentSignRecord(@RequestParam("userId")String userId,
                                                     @RequestParam("courseId")int courseId){
        List<SignRecordBody> signRecordBodies = new ArrayList<>();
        List<Sign> signs = signService.getSignList(courseId);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocationUtils locationUtils = new LocationUtils();
        for(int i = 0; i < signs.size();i++){
            SignRecordBody signRecordBody = new SignRecordBody();
            Sign sign = signs.get(i);
            UserSign userSign = signService.getUserSign(userId,sign.getId());
            signRecordBody.setSignId(sign.getId());
            signRecordBody.setCourseId(courseId);
            String createTime = "";
            try{
                createTime = sdf.format(sign.getCreateTime());
            }catch (Exception e){
                e.printStackTrace();
            }
            signRecordBody.setCreateTime(createTime);

            if(userSign == null){
                if(sign.isAlive()){
                    signRecordBody.setStatus("待签到");
                }else {
                    signRecordBody.setStatus("缺勤");
               }

            }else{
                signRecordBody.setLongitude(String.valueOf(userSign.getLongitude()));
                signRecordBody.setLatitude(String.valueOf(userSign.getLatitude()));
                double distance = locationUtils.getDistance(sign.getLatitude(),sign.getLongitude(),userSign.getLatitude(),userSign.getLongitude());
                if(distance < 2000 || distance > -2000){
                    signRecordBody.setStatus("正常签到");
                }else {
                    signRecordBody.setStatus("位置异常");
                }

            }
            signRecordBody.setAlive(sign.isAlive());
            signRecordBodies.add(signRecordBody);

        }

        return signRecordBodies;
    }


}
