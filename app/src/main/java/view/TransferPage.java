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

public class TransferPage extends AppCompatActivity {

    private TextView from_account_text;
    private TextView to_account_text;
    private TextView amountText;
    private AppCompatButton confirm;
    private AppCompatButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_transfer_page);
        confirm = findViewById(R.id.confirm_butt_transfer);
        back = findViewById(R.id.back_butt_transfer);
        from_account_text = findViewById(R.id.transfer_account_src_id);
        to_account_text = findViewById(R.id.transfer_account_des_id);
        amountText = findViewById(R.id.transfer_amount);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from_account = from_account_text.getText().toString();
                String to_account = to_account_text.getText().toString();
                String amount = amountText.getText().toString();
                if(!from_account.isEmpty() && ! to_account.isEmpty() && !amount.isEmpty()){
                    try{
                        int tempToAcct = Integer.parseInt(to_account);
                        int tempFromAcct = Integer.parseInt(from_account);
                        int tempAmount = Integer.parseInt(amount);
                        ProgressDialog progressDialog = new ProgressDialog(TransferPage.this);
                        int customerId = Integer.parseInt(getIntent().getStringExtra(UserBankingAccountPage.CUSTOMER_ID)) ;
                        new AccountInfoController(TransferPage.this).transfer(tempFromAcct,tempToAcct,tempAmount,progressDialog,customerId);
                        reset();

                    }catch (Exception e){
                        reset();
                        Toast.makeText(TransferPage.this,"At least one input you entered was not the valid",Toast.LENGTH_LONG).show();
                    }
                }else{
                    reset();
                    Toast.makeText(TransferPage.this,"None of the input bars can be empty",Toast.LENGTH_LONG).show();
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
        this.from_account_text.setText("");
        this.to_account_text.setText("");
        this.amountText.setText("");
   }
}