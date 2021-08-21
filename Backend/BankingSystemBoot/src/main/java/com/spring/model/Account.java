package com.spring.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "newAccount",procedureName = "ACCT_OPN"),
                              @NamedStoredProcedureQuery(name = "closeAccount",procedureName = "ACCT_CLS"),
                              @NamedStoredProcedureQuery(name = "deposit",procedureName = "ACCT_DEP"),
                              @NamedStoredProcedureQuery(name = "withdraw",procedureName = "ACCT_WTH"),
                              @NamedStoredProcedureQuery(name = "validateId",procedureName = "PRIVATE_ID"),
                              @NamedStoredProcedureQuery(name = "getCustomerAccounts",procedureName = "CUSTOMER_ACCTS"),
                              @NamedStoredProcedureQuery(name = "transfer",procedureName = "ACCT_TRX" )})
@Data
public class Account {

    @Id
    private int accountId;
    private int id;
    private int balance;
    private char type;
    private char status;
    private int sql_code;
    private String err_msg;


}
