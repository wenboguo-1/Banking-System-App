package com.spring.mapper;

import com.spring.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class UserInfoMapper {

    @Autowired
    private EntityManager entityManager;
    public UserInfo newUser(UserInfo userInfo){
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("createNewUser")
                .registerStoredProcedureParameter(0,String.class,ParameterMode.IN)
                .registerStoredProcedureParameter(1,Character.class,ParameterMode.IN)
                .registerStoredProcedureParameter(2,Integer.class,ParameterMode.IN)
                .registerStoredProcedureParameter(3,Integer.class,ParameterMode.IN)
                .registerStoredProcedureParameter(4,Integer.class,ParameterMode.OUT)
                .registerStoredProcedureParameter(5,Integer.class,ParameterMode.OUT)
                .registerStoredProcedureParameter(6,String.class,ParameterMode.OUT);
        storedProcedureQuery.setParameter(0,userInfo.getP_name());
        storedProcedureQuery.setParameter(1,userInfo.getP_gender());
        storedProcedureQuery.setParameter(2,userInfo.getP_age());
        storedProcedureQuery.setParameter(3,userInfo.getP_pin());
        storedProcedureQuery.execute();
        userInfo.setId((Integer) storedProcedureQuery.getOutputParameterValue(4));
        userInfo.setSql_code((Integer) storedProcedureQuery.getOutputParameterValue(5));
        userInfo.setErr_msg((String) storedProcedureQuery.getOutputParameterValue(6));

       return userInfo;
    }

    public boolean isUserExist(int id, int p_pin){
        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("isUserExist")
                .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN)
                .registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
                .registerStoredProcedureParameter(2,Integer.class,ParameterMode.OUT)
                .registerStoredProcedureParameter(3,Integer.class,ParameterMode.OUT)
                .registerStoredProcedureParameter(4,String.class,ParameterMode.OUT);
        storedProcedureQuery .setParameter(0,id);
        storedProcedureQuery .setParameter(1,p_pin);
        storedProcedureQuery.execute();

        int valid =  (Integer) storedProcedureQuery.getOutputParameterValue(3);
        return valid < 0 ? false : true;
    }

    public UserInfo  getUserInfo(int id){
         StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("getUserInfo")
                 .registerStoredProcedureParameter(0,Integer.class,ParameterMode.IN );
          List<Object[]> rows = storedProcedureQuery.setParameter(0,id).getResultList();
          UserInfo userInfo = new UserInfo();
          for(Object [] row : rows){
              userInfo.setId((Integer) row[0]);
              userInfo.setP_name((String) row[1]);
              userInfo.setP_gender((Character) row[2]);
              userInfo.setP_age((Integer) row[3]);
              userInfo.setP_pin((Integer) row[4]);
          }
         return  userInfo;
    }

}
