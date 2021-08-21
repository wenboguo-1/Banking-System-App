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
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "newClosedHistory",procedureName = "NEW_CLOSED_HIS"),
        @NamedStoredProcedureQuery(name = "getClosedHistory",procedureName = "GET_CLOSED_HIS")})
@Data
public class CloseHistory {
    @Id
    private int customerId;
    private int accountId;
    private String date;

}
