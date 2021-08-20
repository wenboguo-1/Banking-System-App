package view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bankingsystem.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import controller.AccountInfoController;
import controller.UserInfoController;
import module.Account;
import module.UserInfo;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

public class UserBankingAccountPage extends AppCompatActivity {
    private ImageView slideMenuButt;
    private SlideMenu slideMenu;
    private AccountInfoController accountInfoController;
    private TextView customerName;
    private TextView customerId;
    public static RecylerAdapter_Accounts recylerAdapter_accounts;
    private RecyclerView recyclerView;
    private ArrayList<Account> list;
    private TextView openAccount;
    private TextView closeAccount;
    private TextView depositAccount;
    private TextView withdrawAccount;
    private TextView transferAccount;
    private UserInfo userInfo;
    private TextView history;
    public static TextView totalBalanceText;
    public static String CUSTOMER_ID = "INTENT_CUSTOMER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_banking_account_page);
        openAccount = findViewById(R.id.openAccount);
        closeAccount = findViewById(R.id.closeAccount);
        customerName = findViewById(R.id.userName);
        history = findViewById(R.id.history);
        customerId = findViewById(R.id.userId);
        totalBalanceText = findViewById(R.id.totalBalance);
        depositAccount = findViewById(R.id.depositAccount);
        withdrawAccount = findViewById(R.id.withdrawAccount);
        transferAccount = findViewById(R.id.transferAccount);
        recyclerView = findViewById(R.id.accountInfo);
        Intent tempIntent = getIntent();
        userInfo = (UserInfo)tempIntent.getSerializableExtra(UserInfoController.CUSTOMER_INFO);
        list = (ArrayList<Account>) tempIntent.getSerializableExtra(UserInfoController.ACCOUNTS_INFO);
        customerName.setText(String.valueOf(userInfo.getName()));
        customerId.setText( "Id: " + String.valueOf(userInfo.getId()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recylerAdapter_accounts = new RecylerAdapter_Accounts(list);
        recylerAdapter_accounts.setTotalBalance(list);
        recyclerView.setAdapter(recylerAdapter_accounts);
        slideMenuButt = findViewById(R.id.slideWindow);
        slideMenu = findViewById(R.id.slideMenu);

        slideMenuButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });
        openAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(UserBankingAccountPage.this,OpenAccountPage.class);
                 intent.putExtra(CUSTOMER_ID,String.valueOf(userInfo.getId()));
                 startActivity(intent);
            }
        });

        closeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent intent = new Intent(UserBankingAccountPage.this,CloseAccountPage.class);
                   intent.putExtra(CUSTOMER_ID,String.valueOf(userInfo.getId()));
                   startActivity(intent);
            }
        });

        depositAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserBankingAccountPage.this,DepositPage.class);
                intent.putExtra(CUSTOMER_ID,String.valueOf(userInfo.getId()));
                startActivity(intent);
            }
        });

        withdrawAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserBankingAccountPage.this,WithdrawPage.class);
                intent.putExtra(CUSTOMER_ID,String.valueOf(userInfo.getId()));
                startActivity(intent);
            }
        });

        transferAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserBankingAccountPage.this,TransferPage.class);
                intent.putExtra(CUSTOMER_ID,String.valueOf(userInfo.getId()));
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserBankingAccountPage.this,HistoryPage.class);
                intent.putExtra(CUSTOMER_ID,String.valueOf(userInfo.getId()));
                startActivity(intent);
            }
        });

    }





}