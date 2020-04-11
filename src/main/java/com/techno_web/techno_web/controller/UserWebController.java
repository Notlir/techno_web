package com.techno_web.techno_web.controller;

import com.techno_web.techno_web.entities.User;
import com.techno_web.techno_web.services.impl.UserServiceImpl;
import com.techno_web.techno_web.wrapper.LoginWrapper;
import com.techno_web.techno_web.wrapper.TokenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserWebController {

    @Autowired
    UserServiceImpl loUserImpl;

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        LoginWrapper loginWrapper = new LoginWrapper();
        model.addAttribute("loginWrapper", loginWrapper);
        return "registration";
    }

    @GetMapping("/registration/success")
    public ResponseEntity<String> getSuccess(){
        return new ResponseEntity<String>("Registration SUCCEED", HttpStatus.OK);
    }

    @PostMapping(
            path = "/registration",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String handleBrowserSubmissions(LoginWrapper loginWrapper) {

           //loUserImpl.doSignUp(LoginWrapper);
            User loUser = new User();
            loUser.setLogin(loginWrapper.getLogin());
            //TODO salt the password
            loUser.setPassword(loginWrapper.getPassword());
            loUser.setSalt(" ");

            loUserImpl.save(loUser);

        return "redirect:/registration/success";
    }

    @PostMapping(
            path="/login",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody
    TokenWrapper login(@RequestBody LoginWrapper poParams)
    {

        TokenWrapper loResponse = new TokenWrapper();
        loResponse.setToken(loUserImpl.doLogin(poParams));

        return loResponse;
    }
}
