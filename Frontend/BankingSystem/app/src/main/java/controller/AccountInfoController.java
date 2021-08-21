package controller;

import android.accounts.Account;
import android.app.ProgressDialog;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import module.ClosedHistory;
import module.DepositHistory;
import module.History;
import module.HistoryDateSorting;
import module.OpenHistory;
import module.TransferHistory;
import module.UserInfo;
import module.WithdrawHistory;
import view.HistoryPage;
import view.UserBankingAccountPage;

import android.content.Context;
import android.widget.Toast;

public class AccountInfoController {
    private List<Account> accounts;
    private UserInfo userInfo;
    private int userId;
    private Context context;

    public AccountInfoController(Context context){
        this.context = context;
    }

    public void openAccount(int userId,int amount, char type,ProgressDialog progress) {

        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        String url = "http://10.0.2.2:8080/account/newAccount";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", userId);
            jsonObject.put("balance", amount);
            jsonObject.put("type", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int accountId = response.getInt("accountId");

                    newOpenHistory(userId,accountId,amount,type,progress);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "OPS", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public void closeAccount(int id,int customerId,ProgressDialog progress){
        progress.setTitle("Loading");
        progress.setMessage("Please Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        String url = "http://10.0.2.2:8080/account/closeAcc";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if(Integer.parseInt(response.trim()) < 0){
                        Toast.makeText(context,"Your input for accout id is either not correct or " +
                                " does not belong to this customer",Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }else{
                          newCloseHistory(customerId,id,progress);
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"OPS",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                 HashMap<String,String> hashMap = new HashMap<>();
                 hashMap.put("accountId",String.valueOf(id));
                 hashMap.put("customerId",String.valueOf(customerId));
                 return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void deposit(int id,int customerId,int amount,ProgressDialog progress){
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        String url = "http://10.0.2.2:8080/account/deposit";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  if(response.isEmpty() || response == null){
                      Toast.makeText(context,"Ops,there is something worng",Toast.LENGTH_LONG).show();
                      progress.dismiss();
                  }else if(Integer.parseInt(response) < 0){
                      Toast.makeText(context,"The account id you entered was not the correct or the account id you " +
                              "wanted to deposit was closed",Toast.LENGTH_LONG).show();
                      progress.dismiss();
                  }else{
                     newDepositHistory(customerId,id,amount,progress);
                  }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("accountId",String.valueOf(id));
                hashMap.put("balance",String.valueOf(amount));
                return hashMap;
            }
        };

        RequestQueue requestQueue  = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void withdraw(int customerId,int accountId,int amount,ProgressDialog progress){

        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        String url = "http://10.0.2.2:8080/account/withdraw";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                   if(response.length() > 0 ){
                       Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                       progress.dismiss();

                   }
                   else{
                      newWithdrawHistory(customerId,accountId,amount,progress);
                   }
               }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"OPS,there is something wrong",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("accountId",String.valueOf(accountId));
                hashMap.put("customerId",String.valueOf(customerId));
                hashMap.put("amount",String.valueOf(amount));
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
   public void transfer(int from_account,int to_account, int amount,ProgressDialog progress,int customerId){
       progress.setTitle("Loading");
       progress.setMessage("Wait while loading...");
       progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
       progress.show();
       String url = "http://10.0.2.2:8080/account/transfer";
       StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
                 if(response.length() > 0){
                     Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                     progress.dismiss();
                 }else{
                    newTransferHistory(customerId,from_account,to_account,amount,progress);
                 }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(context,"OPS",Toast.LENGTH_LONG).show();
               progress.dismiss();
           }
       }){

           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               HashMap<String,String> hashMap = new HashMap<>();
               hashMap.put("from_account",String.valueOf(from_account));
               hashMap.put("to_account",String.valueOf(to_account));
               hashMap.put("amount",String.valueOf(amount));
               hashMap.put("customerId",String.valueOf(customerId));
               return hashMap;
           }
       };
      RequestQueue requestQueue = Volley.newRequestQueue(context);
      requestQueue.add(stringRequest);
   }

   public void newOpenHistory(int customerId,int accountId,int amount,char type, ProgressDialog progress){

         String url = "http://10.0.2.2:8080/history/open";
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                   if(response.trim().equalsIgnoreCase("Success")){
                       Toast.makeText(context, "You have successfully create new backing account" +
                               ", and your new account id number is " + accountId, Toast.LENGTH_LONG).show();
                       module.Account account = new module.Account(accountId,userId,amount,type,'A');
                       UserBankingAccountPage.recylerAdapter_accounts.addNewAcound(account);
                       progress.dismiss();
                   }

                   progress.dismiss();

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(context, "OPS" + accountId, Toast.LENGTH_LONG).show();
                 progress.dismiss();
             }
         }){
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 HashMap<String,String> hashMap = new HashMap<>();
                 hashMap.put("customerId",String.valueOf(customerId));
                 hashMap.put("accountId",String.valueOf(accountId));
                 return hashMap;
             }
         };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
   }

   public void newCloseHistory(int customerId, int accountId,ProgressDialog progress){
        String url = "http://10.0.2.2:8080/history/close";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if(response.trim().equalsIgnoreCase("Success")){
                        ArrayList<module.Account> accounts = (ArrayList<module.Account>) UserBankingAccountPage.recylerAdapter_accounts.getAccounts();
                        int i = 0;
                        while(true){
                            if(accounts.get(i++).getAccountId() == accountId) {
                                UserBankingAccountPage.recylerAdapter_accounts.setNewStatus(i - 1);
                                break;
                            }
                        }
                        progress.dismiss();
                        Toast.makeText(context,"You have successfully closed your account",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,"Ops, there is something wrong when creating closed history",Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("customerId",String.valueOf(customerId));
                hashMap.put("accountId",String.valueOf(accountId));
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
   }

   public void newDepositHistory(int customerId,int accountId, int amount,ProgressDialog progress){
        String url = "http://10.0.2.2:8080/history/deposit";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                   if(response.trim().equalsIgnoreCase("Success")){
                       Toast.makeText(context,"You have successfully deposited amount $" + amount + " to the account id " +
                               accountId,Toast.LENGTH_LONG).show();
                       ArrayList<module.Account> tempList = (ArrayList<module.Account>) UserBankingAccountPage.recylerAdapter_accounts.getAccounts();
                       for(int i = 0; i < tempList.size();i++){
                           if(tempList.get(i).getAccountId() == accountId){
                               UserBankingAccountPage.recylerAdapter_accounts.addBalance(i,amount);
                               break;
                           }
                       }
                       progress.dismiss();
                   }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("customerId",String.valueOf(customerId));
                hashMap.put("accountId",String.valueOf(accountId));
                hashMap.put("amount",String.valueOf(amount));
                return hashMap;
            }
        };
       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(stringRequest);
   }

   public void newWithdrawHistory(int customerId,int accountId, int amount,ProgressDialog progress){
       String url = "http://10.0.2.2:8080/history/withdraw";
       StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
           public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("Success")){
                    Toast.makeText(context,"You have successfully withdrawn $" + amount + " from account id " + accountId,Toast.LENGTH_LONG).show();
                    ArrayList<module.Account> tempList = (ArrayList<module.Account>) UserBankingAccountPage.recylerAdapter_accounts.getAccounts();
                    for(int i = 0; i < tempList.size();i++){
                        if(tempList.get(i).getAccountId() == accountId){
                            UserBankingAccountPage.recylerAdapter_accounts.subtractBalance(i,amount);
                            break;
                        }
                    }
                    progress.dismiss();
                }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               HashMap<String,String> hashMap = new HashMap<>();
               hashMap.put("customerId",String.valueOf(customerId));
               hashMap.put("accountId",String.valueOf(accountId));
               hashMap.put("amount",String.valueOf(amount));
               return hashMap;
           }
       };
       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(stringRequest);
   }

   public void newTransferHistory(int customerId,int accountSrcId,int accountDesId, int amount,ProgressDialog progress){
         String url = "http://10.0.2.2:8080/history/newTransferHistory";
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                     if(response.trim().equalsIgnoreCase("Success")){
                         Toast.makeText(context,"You have successfully made this transication",Toast.LENGTH_LONG).show();
                         int fromPosition = -1;
                         int toPosition = -1;
                         ArrayList<module.Account> tempList = (ArrayList<module.Account>) UserBankingAccountPage.recylerAdapter_accounts.getAccounts();
                         for(int i = 0; i < tempList.size();i++){

                             if(tempList.get(i).getAccountId() == accountSrcId){
                                 fromPosition = i;
                             }else if(tempList.get(i).getAccountId() == accountDesId){
                                 toPosition = i;
                             }
                             if(fromPosition >= 0 && toPosition >= 0){
                                 UserBankingAccountPage.recylerAdapter_accounts.transferAccount(fromPosition,toPosition,amount);
                                 break;
                             }
                         }
                         progress.dismiss();
                     }else{
                         Toast.makeText(context,"Ops, there is something wrong when updating the history",Toast.LENGTH_LONG).show();
                         progress.dismiss();
                     }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {

             }
         }){
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                  HashMap<String,String> hashMap = new HashMap<>();
                  hashMap.put("customerId",String.valueOf(customerId));
                  hashMap.put("accountSrcId",String.valueOf(accountSrcId));
                  hashMap.put("accountDesId",String.valueOf(accountDesId));
                  hashMap.put("amount",String.valueOf(accountDesId));

                  return hashMap;
             }
         };
         RequestQueue requestQueue = Volley.newRequestQueue(context);
         requestQueue.add(stringRequest);
   }

    public void fetchOpenHistory(int customerId,ProgressDialog progressDialog){
        String url = "http://10.0.2.2:8080/history/getOpenHis/" + customerId;
       StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
                 if(response.isEmpty() || response == null){
                     Toast.makeText(context,"Ops, there is something wrong when feching the data from open history",Toast.LENGTH_LONG).show();
                     progressDialog.dismiss();
                 }else{
                     try {
                         JSONArray jsonArray = new JSONArray(response);
                         ArrayList<History> tempList = HistoryPage.recylerAdpter_history.getHistories();
                         for(int i =0;i < jsonArray.length();i++){
                             JSONObject jsonObject = jsonArray.getJSONObject(i);
                             OpenHistory openHistory = new OpenHistory();
                             openHistory.setAccountId(jsonObject.getInt("accountId"));
                             openHistory.setCustomerId(jsonObject.getInt("customerId"));
                             openHistory.setDate(jsonObject.getString("date"));
                             DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                             openHistory.setDateToNumber(df.parse( openHistory.getDate()).getTime());
                             tempList.add(openHistory);
                         }
                         HistoryPage.recylerAdpter_history.setHistories(tempList);
                         fetchClosedHistory(customerId,progressDialog);
                     } catch (JSONException | ParseException e) {
                         progressDialog.dismiss();
                         e.printStackTrace();
                     }
                 }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               progressDialog.dismiss();
           }
       });
       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(stringRequest);
    }

    public void fetchClosedHistory(int customerId, ProgressDialog progressDialog){
        String url = "http://10.0.2.2:8080/history/getClosedHis/" + customerId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                   if(response == null || response.isEmpty()){
                       Toast.makeText(context,"Ops, there is something wrong when feching the data from open history",Toast.LENGTH_LONG).show();
                       progressDialog.dismiss();
                   }else{
                       try {
                           JSONArray jsonArray = new JSONArray(response);
                           ArrayList<History>tempList = HistoryPage.recylerAdpter_history.getHistories();
                           for(int i = 0; i < jsonArray.length(); i++){
                               JSONObject jsonObject = jsonArray.getJSONObject(i);
                               ClosedHistory closedHistory = new ClosedHistory();
                               closedHistory.setAccountId(jsonObject.getInt("accountId"));
                               closedHistory.setCustomerId(jsonObject.getInt("customerId"));
                               closedHistory.setDate(jsonObject.getString("date"));
                               DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                               closedHistory.setDateToInt(df.parse( closedHistory.getDate()).getTime());
                               tempList.add(closedHistory);
                           }
                           HistoryPage.recylerAdpter_history.setHistories(tempList);
                           fetchDepositHistory(customerId,progressDialog);

                       } catch (JSONException | ParseException e) {
                           e.printStackTrace();
                       }
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

    public void fetchDepositHistory(int customerId, ProgressDialog progressDialog){
        String url = "http://10.0.2.2:8080/history/getDepositHis/" + customerId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response == null || response.isEmpty()){
                    Toast.makeText(context,"Ops, there is something wrong when feching the data from open history",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        ArrayList<History>tempList = HistoryPage.recylerAdpter_history.getHistories();
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            DepositHistory depositHistory = new DepositHistory();
                            depositHistory.setAccountId(jsonObject.getInt("accountId"));
                            depositHistory.setCustomerId(jsonObject.getInt("customerId"));
                            depositHistory.setDate(jsonObject.getString("date"));
                            depositHistory.setAmount(jsonObject.getInt("amount"));
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            depositHistory.setDateToInt(df.parse( depositHistory.getDate()).getTime());
                            tempList.add(depositHistory);
                        }
                        HistoryPage.recylerAdpter_history.setHistories(tempList);
                        fetchWithdrawHistory(customerId,progressDialog);

                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }

        }}}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
   public void fetchWithdrawHistory(int customer, ProgressDialog progressDialog){
        String url = "http://10.0.2.2:8080/history/getWithdrawHis/" + customer;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response == null || response.isEmpty()){
                    Toast.makeText(context,"Ops, there is something wrong when feching the data from open history",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        ArrayList<History>tempList = HistoryPage.recylerAdpter_history.getHistories();
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            WithdrawHistory withdrawHistory = new WithdrawHistory();
                            withdrawHistory.setAccountId(jsonObject.getInt("accountId"));
                            withdrawHistory.setCustomerId(jsonObject.getInt("customerId"));
                            withdrawHistory.setDate(jsonObject.getString("date"));
                            withdrawHistory.setAmount(jsonObject.getInt("amount"));
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            withdrawHistory.setDateToInt(df.parse( withdrawHistory.getDate()).getTime());
                            tempList.add(withdrawHistory);
                        }
                        HistoryPage.recylerAdpter_history.setHistories(tempList);
                        fetchTransferHistory(customer,progressDialog);

                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(stringRequest);
   }

   public void fetchTransferHistory(int customerId, ProgressDialog progressDialog){
        String url = "http://10.0.2.2:8080/history/getTransferHistory/" + customerId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response == null || response.isEmpty()){
                    Toast.makeText(context,"Ops, there is something wrong when fetching the data from history history",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        ArrayList<History> tempList = HistoryPage.recylerAdpter_history.getHistories();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            TransferHistory transferHistory = new TransferHistory();
                            transferHistory.setAccountSrcId(jsonObject.getInt("accountSrcId"));
                            transferHistory.setCustomerId(jsonObject.getInt("customerId"));
                            transferHistory.setDate(jsonObject.getString("date"));
                            transferHistory.setAmount(jsonObject.getInt("amount"));
                            transferHistory.setAccountDesId(jsonObject.getInt("accountDesId"));
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            transferHistory.setTimeToInt(df.parse(transferHistory.getDate()).getTime());
                            tempList.add(transferHistory);
                        }
                        ArrayList<History> histories = HistoryDateSorting.list(tempList);
                        HistoryPage.recylerAdpter_history.setHistories(histories);
                        HistoryPage.recylerAdpter_history.notifyDataSetChanged();
                        progressDialog.dismiss();

                    } catch (JSONException | ParseException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
   }

}
