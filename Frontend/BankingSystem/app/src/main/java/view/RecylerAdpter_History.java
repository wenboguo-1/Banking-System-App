package view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingsystem.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import module.ClosedHistory;
import module.DepositHistory;
import module.History;
import module.OpenHistory;
import module.TransferHistory;
import module.WithdrawHistory;

public class RecylerAdpter_History extends RecyclerView.Adapter<RecylerAdpter_History.ViewHolder>{

    private ArrayList<History> histories;
    private Context context;

    public RecylerAdpter_History(ArrayList<History>histories){

        this.histories = histories;

    }

    public void setHistories(ArrayList<History> histories) {
        this.histories = histories;
    }

    public ArrayList<History> getHistories(){
        return this.histories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecylerAdpter_History.ViewHolder viewHolder;
        View view;
        view = layoutInflater.inflate(R.layout.history_layout, parent, false);
        viewHolder = new RecylerAdpter_History.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerAdpter_History.ViewHolder holder, int position) {

           if(this.histories.get(position) instanceof OpenHistory){
                OpenHistory openHistory = (OpenHistory) this.histories.get(position);
                holder.historyType.setText("Transaction type: Open");
                holder.historyDetail.setText("A new account with id " + openHistory.getAccountId() + " has been opened");
                holder.historyDate.setText("Date: " + openHistory.getDate().substring(0,openHistory.getDate().length()-2) );
                holder.historyAmount.setText("Transaction amount: $0" );
           }else if(this.histories.get(position) instanceof ClosedHistory){
               ClosedHistory closedHistory = (ClosedHistory) this.histories.get((position));
               holder.historyType.setText("Transaction type: Closed");
               holder.historyDetail.setText("A account with id " + closedHistory.getAccountId() + " has been closed");
               holder.historyDate.setText("Date: " +  closedHistory.getDate().substring(0,closedHistory.getDate().length()-2));
               holder.historyAmount.setText("Transaction amount: $0");
           }else if(this.histories.get(position) instanceof DepositHistory){
               DepositHistory depositHistory = (DepositHistory) this.histories.get(position);
               holder.historyType.setText("Transaction type: Deposit");
               holder.historyDetail.setText("A account with id " + depositHistory.getAccountId() + " has been deposited $" + depositHistory.getAmount());
               holder.historyAmount.setText("Transaction amount: $" + depositHistory.getAmount());
               holder.historyDate.setText("Date: " + depositHistory.getDate().substring(0,depositHistory.getDate().length()-2));

           }else if(this.histories.get(position) instanceof WithdrawHistory){
               WithdrawHistory withdrawHistory = (WithdrawHistory) this.histories.get(position);
               holder.historyType.setText("Transaction type: Withdraw");
               holder.historyDetail.setText("A account with id " + withdrawHistory.getAccountId() + " has been withdrawn $" + withdrawHistory.getAmount());
               holder.historyAmount.setText("Transaction amount: " + withdrawHistory.getAmount());
               holder.historyDate.setText("Date: " +  withdrawHistory.getDate().substring(0,withdrawHistory.getDate().length()-2));
           }else{
               TransferHistory transferHistory = (TransferHistory) this.histories.get(position);
               holder.historyType.setText("Transaction Type: Transfer");
               holder.historyDetail.setText("A account with id number " + transferHistory.getAccountSrcId()
                +" transferred amount $" + transferHistory.getAmount() + " to the account with id " + transferHistory.getAccountDesId());
               holder.historyAmount.setText("Transaction amount: $" + transferHistory.getAmount());
               holder.historyDate.setText("Date: " + transferHistory.getDate().substring(0,transferHistory.getDate().length()-2));
           }
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView historyType;
        public TextView historyAmount;
        public TextView historyDetail;
        public TextView historyDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           historyAmount = itemView.findViewById(R.id.historyAmount);
           historyType = itemView.findViewById(R.id.transitionType);
           historyDetail = itemView.findViewById(R.id.historyDetail);
           historyDate = itemView.findViewById(R.id.historyDate);
        }
    }
}
