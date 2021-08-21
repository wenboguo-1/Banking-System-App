package com.spring.mapper;

import com.spring.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.LinkedList;
import java.util.List;

@Repository
public class AccountMapper {

        @Autowired
        private EntityManager entityManager;

        public Account newAccount(Account account){
            StoredProcedureQuery s = entityManager.createNamedStoredProcedureQuery("newAccount")
                    .registerStoredProcedureParameter(0,Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                    .registerStoredProcedureParameter(2,Character.class,ParameterMode.IN)
                    .registerStoredProcedureParameter(3,Integer.class,ParameterMode.OUT)
                    .registerStoredProcedureParameter(4,Integer.class,ParameterMode.OUT)
                    .registerStoredProcedureParameter(5,String.class,ParameterMode.OUT);
                s.setParameter(0,account.getId());
                s.setParameter(1,account.getBalance());
                s.setParameter(2,account.getType());
                account.setAccountId((Integer)s.getOutputParameterValue(3));
                account.setSql_code((Integer) s.getOutputParameterValue(4));
                account.setErr_msg((String) s.getOutputParameterValue(5));

            return account;
        }

        public int closeAcc(int accountId,int customerId){
             StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("closeAccount")
                           .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                           .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                           .registerStoredProcedureParameter(2,Integer.class,ParameterMode.OUT)
                           .registerStoredProcedureParameter(3,String.class,ParameterMode.OUT);
                  storedProcedureQuery.setParameter(0,accountId);
                  storedProcedureQuery.setParameter(1,customerId);

              return (Integer) storedProcedureQuery.getOutputParameterValue(2);
          }
        public int deposit(int accountId, int balance){
              StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("deposit")
                      .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                      .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                      .registerStoredProcedureParameter(2,Integer.class,ParameterMode.OUT)
                      .registerStoredProcedureParameter(3,String.class,ParameterMode.OUT);

                  storedProcedureQuery.setParameter(0,accountId);
                  storedProcedureQuery.setParameter(1,balance);

                  return (Integer) storedProcedureQuery.getOutputParameterValue(2);
        }

        public String withdraw(int accountId,int balance,int userId){
             StoredProcedureQuery storedProcedureQuery1 = entityManager.createNamedStoredProcedureQuery("validateId")
                     .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                     .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                     .registerStoredProcedureParameter(2,Integer.class,ParameterMode.OUT)
                     .registerStoredProcedureParameter(3,String.class,ParameterMode.OUT);

              storedProcedureQuery1.setParameter(0,userId);
              storedProcedureQuery1.setParameter(1,accountId);
            storedProcedureQuery1.execute();
              if((Integer)storedProcedureQuery1.getOutputParameterValue(2) < 0){
                  return (String) storedProcedureQuery1.getOutputParameterValue(3);
              }
            StoredProcedureQuery storedProcedureQuery2 = entityManager.createNamedStoredProcedureQuery("withdraw")
                    .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                    .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                    .registerStoredProcedureParameter(2,Integer.class,ParameterMode.OUT)
                    .registerStoredProcedureParameter(3,String.class,ParameterMode.OUT);
              storedProcedureQuery2.setParameter(0,accountId);
              storedProcedureQuery2.setParameter(1,balance);
              storedProcedureQuery2.execute();
              return (String) storedProcedureQuery2.getOutputParameterValue(3);
        }

        public List<Account> accountList(int userId){
            StoredProcedureQuery s = entityManager.createNamedStoredProcedureQuery("getCustomerAccounts")
                    .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN);
            List<Object[]> rows =  s.setParameter(0,userId).getResultList();
            List <Account> tempList = new LinkedList<>();
            for(Object [] row : rows){
                Account account = new Account();
                account.setAccountId((Integer) row[0]);
                account.setId((Integer) row[1]);
                account.setBalance((Integer) row[2]);
                account.setType((Character) row[3]);
                account.setStatus((Character)row[4]);
                tempList.add(account);
            }

            return tempList;
        }

     public String transfer(int from_account,int to_account,int amount,int customerId){
         StoredProcedureQuery storedProcedureQuery1 = entityManager.createNamedStoredProcedureQuery("validateId")
                 .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                 .registerStoredProcedureParameter(2,Integer.class,ParameterMode.OUT)
                 .registerStoredProcedureParameter(3,String.class,ParameterMode.OUT);

         storedProcedureQuery1.setParameter(0,customerId);
         storedProcedureQuery1.setParameter(1,from_account);

         if((Integer)storedProcedureQuery1.getOutputParameterValue(2) < 0){
             return (String) storedProcedureQuery1.getOutputParameterValue(3);
         }
            StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("transfer")
                    .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                    .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                    .registerStoredProcedureParameter(2,Integer.class,ParameterMode.IN)
                    .registerStoredProcedureParameter(3,Integer.class,ParameterMode.OUT)
                    .registerStoredProcedureParameter(4,String.class,ParameterMode.OUT);

            storedProcedureQuery.setParameter(0,from_account);
            storedProcedureQuery.setParameter(1,to_account);
            storedProcedureQuery.setParameter(2,amount);
            return (String) storedProcedureQuery.getOutputParameterValue(4);
     }
}
