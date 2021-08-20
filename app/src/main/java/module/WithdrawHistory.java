package module;

public class WithdrawHistory extends History {

    private int customerId;
    private int accountId;
    private int amount;
    private String date;
    private long dateToInt;

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDateToInt(long dateToInt) {
        this.dateToInt = dateToInt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getDate() {
        return date;
    }

    public long getDateToInt() {
        return dateToInt;
    }

    @Override
    long getDateTime() {
        return dateToInt;
    }
}
