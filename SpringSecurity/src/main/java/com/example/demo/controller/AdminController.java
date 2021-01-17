package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/demo")
    public String demo(){
        return "I am learning SpringSecurity";
    }

    @GetMapping("/index")
    public String demo1(){
        String name = getUserName();
        return  name+"speaking ：I am learning SpringSecurity hardly";
    }

    private String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        String username = null;
        if(principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        }
        return username;
    }
}
