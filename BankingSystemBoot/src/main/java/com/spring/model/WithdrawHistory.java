package com.spring.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@Table
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "newWithdrawHistory",procedureName = "NEW_WITHDRAW_HISTORY"),
        @NamedStoredProcedureQuery(name = "getWithdrawHistory",procedureName = "GET_WITHDRAW_HISTORY")})
@Data
public class WithdrawHistory {
    @Id
    private int customerId;
    private int accountId;
    private int amount;
    private String date;

}
