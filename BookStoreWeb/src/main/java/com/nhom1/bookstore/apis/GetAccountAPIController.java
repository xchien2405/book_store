package com.nhom1.bookstore.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.services.AccountService;


@Controller
public class GetAccountAPIController {
    private final AccountService accountService;
    
    public GetAccountAPIController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @GetMapping("/api/accounts/{userid}")
    public ResponseEntity<Account> getAccount(@PathVariable("userid") String userid) {
        Account account = accountService.getAccountNonPassword(userid);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
