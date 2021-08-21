package com.spring.controller;

import com.spring.mapper.HistoryMapper;
import com.spring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequestMapping("/history")
@RestController
public class HistoryController {
    @Autowired
    HistoryMapper historyMapper;
    @PostMapping(value = "/open",produces = "application/json")
    public String newOpenHistory(@RequestParam int customerId,@RequestParam int accountId){
        try{

            System.out.println(customerId);
            System.out.println(accountId);
           return historyMapper.newOpenHistory(customerId,accountId);
        }catch (Exception e){
            return "Failed";
        }

    }

    @GetMapping(value = "/getOpenHis/{customerId}",produces = "application/json")
    List<OpenHistory> openHistories(@PathVariable int customerId){
        return historyMapper.openHistoryList(customerId);
    }

   @PostMapping(value = "/close")
    public String newClosedHistory(@RequestParam int customerId, @RequestParam int accountId){
        try{
             return historyMapper.newClosedHis(customerId,accountId);
        }catch (Exception e){
            return "Failed";
        }
   }

    @GetMapping(value = "/getClosedHis/{customerId}",produces = "application/json")
    public List<CloseHistory> closeHistories(@PathVariable int customerId){
        return historyMapper.closedHistoryList(customerId);
    }

    @PostMapping(value = "/deposit")
    public String newDepositHistory(@RequestParam int customerId, @RequestParam int accountId, @RequestParam int amount){

         try{
             return historyMapper.newDepositHistory(customerId,accountId,amount);
         }catch (Exception e){
             return "Failed";
         }
    }
    @GetMapping(value = "/getDepositHis/{customerId}",produces = "application/json")
    public List<DepositHistory> depositHistories(@PathVariable int customerId){
          try{
              return historyMapper.depositHistories(customerId);
          }catch (Exception e){
              return null;
          }
    }

    @PostMapping(value = "/withdraw")
    public String newWithdrawHistory(@RequestParam int customerId, @RequestParam int accountId, @RequestParam int amount){

        try{
            String a =  historyMapper.newWithdrawHistory(customerId,accountId,amount);
            System.out.println(a);
            return a;
        }catch (Exception e){
            return "Failed";
        }
    }
    @GetMapping(value = "/getWithdrawHis/{customerId}",produces = "application/json")
    public List<WithdrawHistory> withdrawHistories(@PathVariable int customerId){
        try{
            return historyMapper.withdrawHistories(customerId);
        }catch (Exception e){
            return null;
        }
    }

    @PostMapping(value = "/newTransferHistory")
    public String newTransferHis(@RequestParam int customerId,@RequestParam int accountSrcId,@RequestParam int accountDesId,
                                 @RequestParam int amount){
        try{
            return historyMapper.newTransferHistory(customerId,accountSrcId,accountDesId,amount);
        }catch (Exception e){
            return "Failed";
        }
    }

    @GetMapping(value = "/getTransferHistory/{customerId}",produces = "application/json")
    public ArrayList<TransferHistory> transferHistories(@PathVariable int customerId){
        return historyMapper.getTransferHistories(customerId);
    }
}
