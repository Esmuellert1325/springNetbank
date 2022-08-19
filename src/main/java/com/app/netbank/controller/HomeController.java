package com.app.netbank.controller;

import com.app.netbank.entity.Transaction;
import com.app.netbank.entity.User;
import com.app.netbank.repos.TransactionRepository;
import com.app.netbank.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepo;

    @GetMapping("index")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) return "admin";

        User user = userRepository.getUserDetails(auth.getName());
        model.addAttribute("balance", user.getBalance() + " Ft");

        return "index";
    }

    // Tranzakciók aloldal
    @GetMapping("transactions")
    public String transactions(Model model) {
        // Get transactions of user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userRepository.getUserDetails(auth.getName()).getId();
        String userEmail = userRepository.getUserDetails(auth.getName()).getEmail();

        List<Transaction> transactionsOfUser = transactionRepo.getAllTransactionsByUserId(userId, userEmail);
        model.addAttribute("allTransactions", transactionsOfUser);
        model.addAttribute("userName", auth.getName());

        return "transactions";
    }

    // Pénz utalása
    @GetMapping("transfer")
    public String transfer() {
        return "transfer";
    }

    @PostMapping("/transferMoney")
    public String transferMoney(@ModelAttribute Transaction trans) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        trans.setTransactionDate(new Date());
        trans.setUserId(userRepository.getUserIdByEmail(auth.getName()));

        List<String> allEmails = userRepository.getAllUserEmails();
        User user = userRepository.getUserDetails(auth.getName());
        Integer currentBalance = user.getBalance();

        if (allEmails.contains(trans.getToUserEmail()) && currentBalance >= trans.getAmount() && !trans.getToUserEmail().equals(auth.getName())) {
            transactionRepo.insertTransaction(trans.getAmount(), trans.getToUserEmail(), trans.getTransactionDate(), trans.getUserId());

            userRepository.subtractMoneyByEmail(trans.getAmount(), auth.getName());
            userRepository.addMoneyByEmail(trans.getAmount(), trans.getToUserEmail());
            return "/transactionMessages/transSuccess";
        }

        return "/transactionMessages/transError";
    }

    // Admin oldal
    @GetMapping("admin")
    public String admin() {
        return "admin";
    }
}
