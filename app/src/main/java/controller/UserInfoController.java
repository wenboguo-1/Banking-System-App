package controller;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import module.UserInfo;
import view.UserBankingAccountPage;
import java.util.List;

public class UserInfoController {
     private Context context;
     private String userId;
     private String userPassword;
     private UserInfo userInfo;
     private List<module.Account> accountList;
     public static String CUSTOMER_INFO = "INTENT_CUSTOMER_INFO";
    public static String ACCOUNTS_INFO = "INTENT_ACCOUNTS_INFO";



     public UserInfoController(Context context){
          this.context = context;
     }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void login(int id, int password){
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
         String url = "http://10.0.2.2:8080/user/login";
         setUserId(String.valueOf(id));
         setUserPassword(String.valueOf(password));
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                   if(response.trim().equals("Success")) {
                        fetchCustomerInfo(Integer.parseInt(getUserId()),progress);

                   }else{
                       Toast.makeText(context, "You either id or pin is not the correct", Toast.LENGTH_LONG).show();
                       progress.dismiss();
                   }
                 Log.e(null,response);
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {

             }
         }){
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 HashMap<String,String> hashMap = new HashMap<>();
                 hashMap.put("id",getUserId());
                 hashMap.put("password",getUserPassword());
                 return hashMap;
             }
         };
         RequestQueue requestQueue = Volley.newRequestQueue(this.context);
         requestQueue.add(stringRequest);
     }

    public String newUser(String userName, char gender, int age, int password){
        JSONObject jsonObject = new JSONObject();
        String url = "http://10.0.2.2:8080/user/newUser";

        try {

            jsonObject.put("p_name", userName);
            jsonObject.put("p_gender",gender);
            jsonObject.put("p_age",age);
            jsonObject.put("p_pin",password);

        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int sql_code = response.getInt("sql_code");

                    if(sql_code < 0){
                        Toast.makeText(context,"OPS,there is something wrong", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,"You have successfully created a user account, and your id number is " + response.getInt("id"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.add(jsonObjectRequest);

        return userId;
    }

    private void fetchCustomerInfo(int id,ProgressDialog progressDialog)  {

        String url = "http://10.0.2.2:8080/user/getCustomerInfo/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    userInfo = new UserInfo();
                    userInfo.setId(jsonObject.getInt("id"));
                    userInfo.setAge(jsonObject.getInt("p_age"));
                    userInfo.setName(jsonObject.getString("p_name"));
                    userInfo.setGender(jsonObject.getString("p_gender").charAt(0));
                    Intent intent = new Intent(context,UserBankingAccountPage.class);
                    fetchAccountList(intent,progressDialog,id,userInfo);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.add(stringRequest);

    }

    private void fetchAccountList(Intent intent, ProgressDialog progressDialog, int id, UserInfo userInfo){
          String url = "http://10.0.2.2:8080/account/getCustomerAccounts/" + id;
          StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  try {
                      JSONArray jsonArray = new JSONArray(response);
                      accountList = new LinkedList<>();
                      for(int i = 0; i < jsonArray.length(); i++ ){
                          module.Account account = new module.Account();
                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                          account.setAccountId(jsonObject.getInt("accountId"));
                          account.setId(jsonObject.getInt("id"));
                          account.setBalance(jsonObject.getInt("balance"));
                          account.setType(jsonObject.getString("type").charAt(0));
                          account.setStatus(jsonObject.getString("status").charAt(0));
                          accountList.add(account);
                      }
                      intent.putExtra(CUSTOMER_INFO,userInfo);
                      intent.putExtra(ACCOUNTS_INFO,(Serializable) accountList);
                      context.startActivity(intent);
                      progressDialog.dismiss();
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {

              }
          });
          RequestQueue requestQueue = Volley.newRequestQueue(context);
          requestQueue.add(stringRequest);
    }
}
