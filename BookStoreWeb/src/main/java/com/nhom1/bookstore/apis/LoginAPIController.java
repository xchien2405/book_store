package com.nhom1.bookstore.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.services.AccountService;


@RestController
public class LoginAPIController {
    private final AccountService accountService;

    public LoginAPIController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping("/api/login")
    public ResponseEntity<Account> authentication(@RequestParam("username") String username, @RequestParam("password") String password) {
        int ketqua = accountService.authentication(username, password);
        Account response = new Account();
        if (ketqua != 0) {
            if (ketqua >= 1) {
                response.setUserID(username);
                if(ketqua > 1) {
                    response.setAdmin(true);
                } else{
                    response.setAdmin(false);
                }
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
