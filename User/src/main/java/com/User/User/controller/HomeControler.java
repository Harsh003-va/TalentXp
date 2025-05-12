package com.User.User.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeControler {

@GetMapping("/greet")
    public String greet(){
    return "Welcome to Spring Security";
}


@GetMapping("/csrf-token")
    public CsrfToken getCsrf(HttpServletRequest req){
    return (CsrfToken) req.getAttribute("_csrf");
}
}
