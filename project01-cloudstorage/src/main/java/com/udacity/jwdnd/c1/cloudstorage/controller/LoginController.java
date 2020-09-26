package com.udacity.jwdnd.c1.cloudstorage.controller;

import com.udacity.jwdnd.c1.cloudstorage.model.User;
import com.udacity.jwdnd.c1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private UserService userService;
    public LoginController(UserService userService) {
        this.userService = userService;

        // create a test user temporarily, should be deleted once all features are done
//        if (this.userService.isUsernameAvailable("admin")) {
//            this.userService.createUser(new User(null, "admin", "", "123", "keyun", "shang"));
//        }
    }

    @GetMapping()
    public String loginView() {
        return "login";
    }
}
