package com.nhom1.bookstore.apis;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.services.AccountService;

@RestController
public class EditPersonalAccountAPIController {
    private final AccountService accountService;
    
    public EditPersonalAccountAPIController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/api/accounts/{userid}")
    public void changeInformation(@PathVariable("userid") String userid, @RequestBody Account newAccount) {        
            Account currentAccount = accountService.getAccount(userid);
        
        if(newAccount.getUserName() != null) {
            newAccount.setUserPhone(currentAccount.getUserPhone());
            newAccount.setUserAddress(currentAccount.getUserAddress());
            newAccount.setUserEmail(currentAccount.getUserEmail());   

        } else if(newAccount.getUserPhone() != null) {
            newAccount.setUserName(currentAccount.getUserName());
            newAccount.setUserAddress(currentAccount.getUserAddress());
            newAccount.setUserEmail(currentAccount.getUserEmail());  

        } else if(newAccount.getUserAddress() != null) {
            newAccount.setUserName(currentAccount.getUserName());
            newAccount.setUserPhone(currentAccount.getUserPhone());
            newAccount.setUserEmail(currentAccount.getUserEmail());  

        } else if(newAccount.getUserEmail() != null) {
            newAccount.setUserName(currentAccount.getUserName());
            newAccount.setUserPhone(currentAccount.getUserPhone());
            newAccount.setUserAddress(currentAccount.getUserAddress());
        }

        accountService.editAccount(currentAccount.getUserID(), newAccount);
    }
}