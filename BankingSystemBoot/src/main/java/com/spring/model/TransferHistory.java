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
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "newTransferHistory",procedureName = "NEW_TRANSFER_HISTORY"),
        @NamedStoredProcedureQuery(name = "getTransferHistory",procedureName = "GET_TRANSFER_HISTORY")})
@Data
public class TransferHistory {
    @Id
    private int customerId;
    private int accountSrcId;
    private int accountDesId;
    private int amount;
    private String date;

}
