package com.app.netbank.controller;

import com.app.netbank.entity.User;
import com.app.netbank.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("index")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) return "admin";

        User user = userRepository.getUserDetails(auth.getName());
        model.addAttribute("balance", user.getBalance() + " Ft");

        return "index";
    }

    @GetMapping("transactions")
    public String transactions() {
        return "transactions";
    }

    @GetMapping("transfer")
    public String transferMoney() {
        //TODO: Transfer money
        return "transfer";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin";
    }
}
