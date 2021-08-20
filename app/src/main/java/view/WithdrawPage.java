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

public class WithdrawPage extends AppCompatActivity {
    private TextView accountIdText;
    private TextView amountText;
    private AppCompatButton confirm;
    private AppCompatButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_withdraw_page);
        accountIdText = findViewById(R.id.withdraw_account_id);
        amountText = findViewById(R.id.withdraw_amount);
        confirm = findViewById(R.id.confirm_butt_withdraw);
        back = findViewById(R.id.back_butt_withdraw);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = accountIdText.getText().toString();
                String amount = amountText.getText().toString();
                if(accountId.length() > 0 && amount.length() > 0){
                    try{
                        int tempId = Integer.valueOf(accountId);
                        int tempAmount = Integer.valueOf(amount);
                        int tempCustomerId = Integer.valueOf(getIntent().getStringExtra(UserBankingAccountPage.CUSTOMER_ID));
                        ProgressDialog progressDialog = new ProgressDialog(WithdrawPage.this);
                        new AccountInfoController(WithdrawPage.this).withdraw(tempCustomerId,tempId,tempAmount,progressDialog);
                        reset();

                    }catch (Exception e){
                        Toast.makeText(WithdrawPage.this,"Your input is not the valid",Toast.LENGTH_LONG).show();
                        reset();

                    }
                }else{
                    Toast.makeText(WithdrawPage.this,"None of your input bar can be empty",Toast.LENGTH_LONG).show();
                    reset();
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
        this.amountText.setText("");
        this.accountIdText.setText("");
    }
}