package view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankingsystem.R;

import controller.AccountInfoController;

public class DepositPage extends AppCompatActivity {
    private TextView account_id;
    private TextView amountText;
    private AppCompatButton confirm;
    private AppCompatButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_banking_account_page);
        setContentView(R.layout.activity_close_account_page);
        setContentView(R.layout.activity_deposit_page);
        account_id = findViewById(R.id.deposit_account_id);
        amountText = findViewById(R.id.deposit_amoung);
        confirm = findViewById(R.id.confirm_butt_deposit);
        back = findViewById(R.id.back_butt_deposit);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String accountId = account_id.getText().toString();
                 String amount = amountText.getText().toString();
                 if(accountId.length() > 0 && amount.length() > 0 ){
                      try{
                          int tempId = Integer.valueOf(accountId);
                          int tempAmount = Integer.valueOf(amount);
                          if(tempAmount < 0){
                              Toast.makeText(DepositPage.this,"Amount can not be negative",Toast.LENGTH_LONG);
                              reset();
                          }else{
                              ProgressDialog progressDialog = new ProgressDialog(DepositPage.this);
                              int customerId =Integer.parseInt(getIntent().getStringExtra(UserBankingAccountPage.CUSTOMER_ID));
                              new AccountInfoController(DepositPage.this).deposit(tempId,customerId,tempAmount,progressDialog);
                              reset();
                          }
                      }catch (Exception e){
                           Toast.makeText(DepositPage.this,"Your input is not the valid",Toast.LENGTH_LONG);
                           reset();
                      }
                 }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void reset(){
        account_id.setText("");
        amountText.setText("");
    }
}