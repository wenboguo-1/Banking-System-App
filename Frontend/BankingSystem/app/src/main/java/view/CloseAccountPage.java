package view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bankingsystem.R;
import controller.AccountInfoController;

public class CloseAccountPage extends AppCompatActivity {

    private TextView close_acctId;
    private AppCompatButton confirm;
    private AppCompatButton close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_banking_account_page);
        setContentView(R.layout.activity_close_account_page);
        close_acctId = findViewById(R.id.close_accountId);
        confirm = findViewById(R.id.confirm_butt_close);
        close = findViewById(R.id.back_butt_close);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acctId = close_acctId.getText().toString();
                try{
                  int id =  Integer.parseInt(acctId);
                  AccountInfoController accountInfoController = new AccountInfoController(CloseAccountPage.this);
                  ProgressDialog progressDialog = new ProgressDialog(CloseAccountPage.this);
                    Intent intent = getIntent();
                    int customerId = Integer.parseInt(intent.getStringExtra(UserBankingAccountPage.CUSTOMER_ID)) ;
                  accountInfoController.closeAccount(id,customerId,progressDialog);
                  rest();
                }catch (Exception e){
                    Toast.makeText(CloseAccountPage.this,"Your input for the account id number is not correct",Toast.LENGTH_LONG).show();;
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void rest(){
        this.close_acctId.setText("");
    }
}