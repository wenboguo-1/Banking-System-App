package com.spring.mapper;

import com.spring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Repository
public class HistoryMapper {
     @Autowired
     EntityManager entityManager;

     public String newOpenHistory(int customerId,int accountId) throws Exception{
         java.util.Date dt = new java.util.Date();
         java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
         String currentTime = sdf.format(dt);
         StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("newOpenHistory")
                 .registerStoredProcedureParameter(0,Integer.class, ParameterMode.IN)
                 .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(2,String.class,ParameterMode.IN);

         storedProcedureQuery.setParameter(0,customerId);
         storedProcedureQuery.setParameter(1,accountId);
         storedProcedureQuery.setParameter(2,currentTime);
         storedProcedureQuery.execute();
         return "Success";
     }

     public List<OpenHistory> openHistoryList(int customerId){
           StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("getOpenHistory")
                   .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN);
          List<Object[]> rows = storedProcedureQuery.setParameter(0,customerId).getResultList();
         ArrayList<OpenHistory> tempList = new ArrayList<>();
          for(Object[] row : rows ){
               OpenHistory openHistory = new OpenHistory();
               openHistory.setCustomerId((Integer)row[0]);
               openHistory.setAccountId((Integer)row[1]);
               openHistory.setDate(row[2].toString());
               tempList.add(openHistory);
          }
          return tempList;
     }

     public String newClosedHis(int customerId, int accountId){
         java.util.Date dt = new java.util.Date();
         java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
         String currentTime = sdf.format(dt);
         StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("newClosedHistory")
                 .registerStoredProcedureParameter(0,Integer.class, ParameterMode.IN)
                 .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(2,String.class,ParameterMode.IN);

         storedProcedureQuery.setParameter(0,customerId);
         storedProcedureQuery.setParameter(1,accountId);
         storedProcedureQuery.setParameter(2,currentTime);
         storedProcedureQuery.execute();
         return "Success";

     }

    public List<CloseHistory> closedHistoryList(int customerId){
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("getClosedHistory")
                .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN);
        List<Object[]> rows = storedProcedureQuery.setParameter(0,customerId).getResultList();
        ArrayList<CloseHistory> tempList = new ArrayList<>();
        for(Object[] row : rows ){
            CloseHistory closeHistory = new CloseHistory();
            closeHistory.setCustomerId((Integer)row[0]);
            closeHistory.setAccountId((Integer)row[1]);
            closeHistory.setDate(row[2].toString());
            tempList.add(closeHistory);
        }
        return tempList;
    }

    public String newDepositHistory(int customerId,int accountId, int amount){
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        String currentTime = sdf.format(dt);
         StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("newDepositHistory")
                 .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(2,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(3,String.class,ParameterMode.IN);
         storedProcedureQuery.setParameter(0,customerId);
         storedProcedureQuery.setParameter(1,accountId);
         storedProcedureQuery.setParameter(2,amount);
         storedProcedureQuery.setParameter(3,currentTime);
         storedProcedureQuery.execute();
         return "Success";
    }

    public List<DepositHistory> depositHistories(int customerId){
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("getDepositHistory")
                .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN);
        List<Object[]> rows = storedProcedureQuery.setParameter(0,customerId).getResultList();
        ArrayList<DepositHistory> tempList = new ArrayList<>();
        for(Object[] row : rows ){
            DepositHistory depositHistory = new DepositHistory();
            depositHistory.setCustomerId((Integer)row[0]);
            depositHistory.setAccountId((Integer)row[1]);
            depositHistory.setAmount((Integer)row[2]);
            depositHistory.setDate(row[3].toString());
            tempList.add(depositHistory);
        }
        return tempList;
    }
    public String newWithdrawHistory(int customerId,int accountId, int amount){
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        String currentTime = sdf.format(dt);
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("newWithdrawHistory")
                .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                .registerStoredProcedureParameter(2,Integer.class,ParameterMode.IN)
                .registerStoredProcedureParameter(3,String.class,ParameterMode.IN);
        storedProcedureQuery.setParameter(0,customerId);
        storedProcedureQuery.setParameter(1,accountId);
        storedProcedureQuery.setParameter(2,amount);
        storedProcedureQuery.setParameter(3,currentTime);
       System.out.println(storedProcedureQuery.execute());
        return "Success";
    }
    public List<WithdrawHistory> withdrawHistories(int customerId){
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("getWithdrawHistory")
                .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN);
        List<Object[]> rows = storedProcedureQuery.setParameter(0,customerId).getResultList();
        ArrayList<WithdrawHistory> tempList = new ArrayList<>();
        for(Object[] row : rows ){
            WithdrawHistory withdrawHistory = new WithdrawHistory();
            withdrawHistory.setCustomerId((Integer)row[0]);
            withdrawHistory.setAccountId((Integer)row[1]);
            withdrawHistory.setAmount((Integer)row[2]);
            withdrawHistory.setDate(row[3].toString());
            tempList.add(withdrawHistory);
        }
        return tempList;
    }

    public String newTransferHistory(int customerId, int accountSrcId,int accountDesId,int amount){
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        String currentTime = sdf.format(dt);
         StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("newTransferHistory")
                 .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(2,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(3,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(4,String.class,ParameterMode.IN);
           storedProcedureQuery.setParameter(0,customerId);
           storedProcedureQuery.setParameter(1,accountSrcId);
           storedProcedureQuery.setParameter(2,accountDesId);
           storedProcedureQuery.setParameter(3,amount);
           storedProcedureQuery.setParameter(4,currentTime);
           storedProcedureQuery.execute();
           return "Success";
    }

    public ArrayList<TransferHistory> getTransferHistories(int customer){
         StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("getTransferHistory")
                 .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN);
         List<Object []> rows =  storedProcedureQuery.setParameter(0,customer).getResultList();
         ArrayList<TransferHistory> histories = new ArrayList<>();
         for(Object [] row : rows){
             TransferHistory transferHistory = new TransferHistory();
             transferHistory.setCustomerId(Integer.parseInt(row[0].toString()));
             transferHistory.setAccountSrcId(Integer.parseInt(row[1].toString()));
             transferHistory.setAccountDesId(Integer.parseInt(row[2].toString()));
             transferHistory.setAmount(Integer.parseInt(row[3].toString()));
             transferHistory.setDate(row[4].toString());
             histories.add(transferHistory);
         }

         return histories;
    }
}
