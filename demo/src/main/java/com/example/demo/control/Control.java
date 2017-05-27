package com.example.demo.control;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/5/25.
 */
@RestController
public class Control {
    @RequestMapping(value = "/demo",method = RequestMethod.GET)
    public String f(){
        return "demo";
    }

}
