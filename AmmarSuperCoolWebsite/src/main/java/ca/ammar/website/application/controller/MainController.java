package ca.ammar.website.application.controller;


import ca.ammar.website.application.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/ammarWebsite")
public class MainController {


    private DatabaseAccess da;

    @Autowired
    private void databaseAccess (DatabaseAccess databaseAccess){
        this.da = databaseAccess;
    }

    @GetMapping()
    public String index(){
        return "/mainPage/index";
    }

    @GetMapping("/Dashboard")
    public String dashboard(){
        return "/mainPage/dashboard";
    }

    @GetMapping("/login")
    public String login(){
        return "/login/loginPage";
    }

    @GetMapping("/aboutMe")
    public String aboutMe(){
        return "/mainPage/aboutMe";
    }


    @GetMapping("/error")
    public String error(){
        return "error/error";
    }
}
