package com.spring.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Getter
@Setter
@Table
@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery( name = "createNewUser",procedureName = "CUST_CRT"),
                              @NamedStoredProcedureQuery(name = "isUserExist",procedureName = "CUST_LOGIN"),
                              @NamedStoredProcedureQuery(name = "getUserInfo", procedureName = "USER_INFO")})

@Data
public class UserInfo {
      @Id
      private int id;
      private String p_name;
      private char p_gender;
      private int p_age;
      private int p_pin;
      private int sql_code;
      private String err_msg;

}
