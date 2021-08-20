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
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "newDepositHistory",procedureName = "NEW_DEPOSIT_HISTORY"),
        @NamedStoredProcedureQuery(name = "getDepositHistory",procedureName = "GET_DEPOSIT_HISTORY")})
@Data
public class DepositHistory {
    @Id
    private int customerId;
    private int accountId;
    private int amount;
    private String date;

}
