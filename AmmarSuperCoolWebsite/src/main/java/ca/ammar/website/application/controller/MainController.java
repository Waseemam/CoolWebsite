package ca.ammar.website.application.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/")
    public String index(){
        return "/mainPage/index";
    }


    @GetMapping("/Dashboard")
    public String dashboard(){
        return "/user/dashboard";
    }


    @GetMapping("/login")
    public String login(){
        return "/login/loginPage";
    }

    @GetMapping("/aboutMe")
    public String aboutMe(){
        return "/mainPage/aboutMe";
    }
}
