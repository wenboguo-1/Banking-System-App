package view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingsystem.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

import controller.AccountInfoController;
import module.History;
import module.OpenHistory;

public class HistoryPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static RecylerAdpter_History recylerAdpter_history;
    private ImageView historyBackButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history_page);
        historyBackButt = findViewById(R.id.backButtHistory);
        recyclerView = findViewById(R.id.historyInfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<History> arrayList = new ArrayList<>();
        recylerAdpter_history = new RecylerAdpter_History(arrayList);
        recyclerView.setAdapter(recylerAdpter_history);
        new AccountInfoController(this).fetchOpenHistory(Integer.parseInt(getIntent().getStringExtra(UserBankingAccountPage.CUSTOMER_ID)),progress);
        recylerAdpter_history.notifyDataSetChanged();
        historyBackButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}