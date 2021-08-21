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
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "newOpenHistory",procedureName = "NEW_OPN_HIS"),
                              @NamedStoredProcedureQuery(name = "getOpenHistory",procedureName = "GET_OPEN_HIS")})
@Data
public class OpenHistory {
      @Id
      private int customerId;
      private int accountId;
      private String date;

}
