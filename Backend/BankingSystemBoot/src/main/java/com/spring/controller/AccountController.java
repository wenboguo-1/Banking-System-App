package com.spring.controller;

import com.spring.mapper.AccountMapper;
import com.spring.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/account")
@RestController
public class AccountController {
     @Autowired
    AccountMapper accountMapper;
     @PostMapping(value = "/newAccount",produces = "application/json")
     public Account newAccount(@RequestBody Account account){
         return accountMapper.newAccount(account);
     }

     @PostMapping(value = "/closeAcc")
     public int classAcc(@RequestParam int accountId,@RequestParam int customerId){
          return accountMapper.closeAcc(accountId,customerId);
     }

     @PostMapping(value = "/deposit")
     public int deposit(@RequestParam int accountId, @RequestParam int balance){
          return balance <= 0 ? -1 : accountMapper.deposit(accountId,balance);
     }
     @PostMapping(value =  "/withdraw")
    public String withdraw(@RequestParam int accountId, @RequestParam int amount,@RequestParam int customerId){
           return accountMapper.withdraw(accountId,amount,customerId);
     }

     @GetMapping(value = "/getCustomerAccounts/{id}")
    public List<Account> accountList(@PathVariable int id){
         System.out.println("123");
         return accountMapper.accountList(id);
     }

     @PostMapping(value = "/transfer")
    public String transfer(@RequestParam int from_account,@RequestParam int to_account, @RequestParam int amount,@RequestParam int customerId){
         return accountMapper.transfer(from_account,to_account,amount,customerId);
     }
}
