package ru.ifmo.cs.api_gateway.auth.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.api_gateway.jwt.application.service.JwtService;
import ru.ifmo.cs.misc.UserId;

@RestController
@RequestMapping("test/")
@AllArgsConstructor
public class TestController {

    @GetMapping("/welcome")
    public String welcome(){
        return "hello world";
    }

    @GetMapping("/users")
    public String pageForUser(){
        return "This is page for only users";
    }


    @GetMapping("/admins")
    public String pageForAdmins(){
        return "This is page for only admins";
    }


    @GetMapping("/all")
    public String pageForAll(){
        return "This is page for all employees";
    }

}
