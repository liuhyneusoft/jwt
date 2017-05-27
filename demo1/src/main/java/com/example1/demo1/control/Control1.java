package com.example1.demo1.control;

import com.example1.demo1.domain.User;
import com.example1.demo1.jwt.JwtUtil;
import com.example1.demo1.service.LoginService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/5/25.
 */
@RestController
public class Control1 {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/demo1/{p}",method = RequestMethod.GET)
    public String f(@PathVariable(name = "p") String p){
        return "demo1="+p;
    }

    @RequestMapping(value = "/demo1/p",method = RequestMethod.POST)
    public String f1(@RequestBody String p){
        return "demo1p="+p;
    }


    @RequestMapping(value = "/demo1/login",method = RequestMethod.POST)
    public String login(@RequestBody User u){
        String ret;
        if(loginService.checkUser(u.getUserName(),u.getUserPw())){
            ret = JwtUtil.generateToken(u.getUserName());
        }else{
            ret = "ERROR";
        }
        return ret;
    }


    @RequestMapping(value = "/demo1/call",method = RequestMethod.POST)
    public String call(@RequestBody String json,HttpServletRequest req, HttpServletResponse resp) {
        String ret = "";
        try{
            JSONObject jsonParam = new JSONObject(json);
            String token = (String)jsonParam.get("token");
            String data = (String)jsonParam.get("data");
            String username = JwtUtil.validateToken(token);
        }catch(JSONException j){

        }catch(RuntimeException e){
            ret = "invalid name";
        }
        return "ppppp==="+ret;
    }

}