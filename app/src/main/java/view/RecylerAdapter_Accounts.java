package view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.bankingsystem.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import module.Account;
import java.util.List;
public class RecylerAdapter_Accounts extends  RecyclerView.Adapter<RecylerAdapter_Accounts.ViewHolder> {

    private Context context;
    private ArrayList<Account> accounts;
    private int totalBalance;

    public RecylerAdapter_Accounts(ArrayList<Account>accounts){
        this.accounts = accounts;
    }

    @NonNull

    @Override
    public RecylerAdapter_Accounts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecylerAdapter_Accounts.ViewHolder viewHolder;
        View view;
        view = layoutInflater.inflate(R.layout.account_layout, parent, false);
        viewHolder = new RecylerAdapter_Accounts.ViewHolder(view);
        context = parent.getContext();


        return viewHolder;
    }

  public void addNewAcound(Account account){
        accounts.add(account);
       this.totalBalance += account.getBalance();
       UserBankingAccountPage.totalBalanceText.setText("Total balance: $"+this.totalBalance);
        notifyDataSetChanged();
  }
  public void setNewStatus(int position){
        accounts.get(position).setStatus('I');
        this.totalBalance -= accounts.get(position).getBalance();
        UserBankingAccountPage.totalBalanceText.setText("Total balance: $"+this.totalBalance);
        accounts.get(position).setBalance(0);
        notifyDataSetChanged();
  }
  public void addBalance(int position,int amount){
        accounts.get(position).setBalance(accounts.get(position).getBalance() + amount);
        this.totalBalance += + amount;
      UserBankingAccountPage.totalBalanceText.setText("Total balance: $"+this.totalBalance);
        notifyDataSetChanged();
  }

  public void subtractBalance(int position,int amount){
        accounts.get(position).setBalance(accounts.get(position).getBalance() - amount);
        this.totalBalance -= + amount;
        UserBankingAccountPage.totalBalanceText.setText(String.valueOf("Total balance: $"+this.totalBalance));
        notifyDataSetChanged();
  }

  public void transferAccount(int fromPosition, int toPosition,int amount){
        int fromBalance = accounts.get(fromPosition).getBalance();
        int toBalance = accounts.get(toPosition).getBalance();
        accounts.get(fromPosition).setBalance(fromBalance - amount);
        accounts.get(toPosition).setBalance(toBalance + amount);
        notifyDataSetChanged();
  }

  public List<Account> getAccounts(){
        return accounts;
  }
    @Override
    public void onBindViewHolder(@NonNull RecylerAdapter_Accounts.ViewHolder holder, int position) {
          if(this.accounts.get(position).getType() == 'C'){
              holder.accountType.setText("EVERYDAY CHECKING");
          }else{
              holder.accountType.setText("EVERYDAY Saving");
          }
          holder.accountStatus.setText("Status: " + (this.accounts.get(position).getStatus() == 'A' ? "open" : "close") );
          holder.accountBalance.setText("$" + this.accounts.get(position).getBalance() + ".00");
          holder.accountId.setText("Id: " + this.accounts.get(position).getAccountId());


    }

    public void setTotalBalance(ArrayList<Account> accounts) {
        int balance = 0;
        for(Account account: accounts){
            balance += account.getBalance();
        }
        this.totalBalance = balance;
        UserBankingAccountPage.totalBalanceText.setText("Total balance $" + balance);
    }

    @Override
    public int getItemCount() {
        return this.accounts.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView accountId;
        public TextView accountBalance;
        public TextView accountType;
        public TextView accountStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            accountId = itemView.findViewById(R.id.accountId);
            accountBalance = itemView.findViewById(R.id.accountBalance);
            accountStatus = itemView.findViewById(R.id.accountStatus);
            accountType = itemView.findViewById(R.id.accountType);
        }
    }
}
