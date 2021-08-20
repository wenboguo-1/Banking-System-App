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

public class OpenAccountPage extends AppCompatActivity {

    private TextView newType;
    private TextView newBalance;
    private AppCompatButton confirmButt;
    private AppCompatButton backButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_open_account_page);
        newType = findViewById(R.id.deposit_amoung);
        newBalance = findViewById(R.id.newBalance);
        confirmButt = findViewById(R.id.confirm_butt);
        backButt = findViewById(R.id.back_butt);
        Intent intent = getIntent();
        String id = intent.getStringExtra(UserBankingAccountPage.CUSTOMER_ID);


        confirmButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = newType.getText().toString();
                String balance = newBalance.getText().toString();
                if(type.isEmpty() || balance.isEmpty() ){
                    Toast.makeText(getApplicationContext(),"None of these input bars can be empty",Toast.LENGTH_LONG).show();
                    return;
                }else if(type.toLowerCase().charAt(0) != 'c' && type.toLowerCase().charAt(0) != 's'){
                    Toast.makeText(getApplicationContext(),"Your type is not the valid",Toast.LENGTH_LONG).show();
                    return;
                }

                try{
                    Integer.parseInt(balance);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Your balance is not the valid",Toast.LENGTH_LONG);
                    rest();
                    return;
                }
                AccountInfoController accountInfoController = new AccountInfoController(OpenAccountPage.this);
                ProgressDialog progress = new ProgressDialog(OpenAccountPage.this);
                accountInfoController.openAccount(Integer.valueOf(id),Integer.valueOf(balance),type.toUpperCase().charAt(0),progress);

                rest();
            }
        });
       backButt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    public void rest(){
        newType.setText("");
        newBalance.setText("");
    }
}