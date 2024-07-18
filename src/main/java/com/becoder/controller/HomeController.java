package com.becoder.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.becoder.dao.UserDao;
import com.becoder.entity.User;

@Controller
public class HomeController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping(path = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute User user, HttpSession session) {
        userDao.saveUser(user);
        session.setAttribute("msg", "Register Successfully");
        return "redirect:/register";
    }

    @RequestMapping(path = "/loginUser", method = RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
                            HttpSession session) {
        User user = userDao.login(email, password);

        if (user != null) {
            session.setAttribute("userObj", user);
            return "home";
        } else {
            session.setAttribute("msg", "Email & password invalid");
            return "redirect:/login";
        }
    }
}
