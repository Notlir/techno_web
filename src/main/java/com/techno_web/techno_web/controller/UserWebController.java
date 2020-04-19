package com.techno_web.techno_web.controller;

import com.techno_web.techno_web.services.impl.UserServiceImpl;
import com.techno_web.techno_web.wrapper.LoginWrapper;
import com.techno_web.techno_web.wrapper.TokenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserWebController {

    @Autowired
    UserServiceImpl loUserImpl;

    @GetMapping(path = "/registration/success")
    public ResponseEntity<String> getSuccess() {
        return new ResponseEntity<String>("Registration SUCCEED", HttpStatus.OK);
    }

    @GetMapping(path = "/registration/failed")
    public ResponseEntity<String> getfailure() {
        return new ResponseEntity<String>("Registration FAILED", HttpStatus.OK);
    }

    @PostMapping(
            path = "/registration",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String handleBrowserSubmissions(LoginWrapper loginWrapper) {
        try {
            loUserImpl.doSignUp(loginWrapper);
        } catch (Exception loE) {
            return "redirect:/registration/failed";
        }
        return "redirect:/loginPage";
    }

    @PostMapping(
            path = "/login",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String login(LoginWrapper loginWrapper, HttpServletResponse response) {
        Cookie cookie = new Cookie("Authorization", loUserImpl.doLogin(loginWrapper));
        response.addCookie(cookie);
        return "redirect:/getSeriesForMe";
    }

    @PostMapping(
            path="/logout",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String logout(@CookieValue("Authorization") String token)
    {
        loUserImpl.doLogout(token);

        return "redirect:/loginPage";
    }
}
