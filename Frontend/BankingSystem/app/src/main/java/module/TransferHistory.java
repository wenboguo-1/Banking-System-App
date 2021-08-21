package module;

public class TransferHistory extends History {
    private int customerId;
    private int accountSrcId;
    private int accountDesId;
    private int amount;
    private long timeToInt;
    private String date;

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setAccountSrcId(int accountSrcId) {
        this.accountSrcId = accountSrcId;
    }

    public void setAccountDesId(int accountDesId) {
        this.accountDesId = accountDesId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTimeToInt(long timeToInt) {
        this.timeToInt = timeToInt;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getAccountSrcId() {
        return accountSrcId;
    }

    public int getAccountDesId() {
        return accountDesId;
    }

    public int getAmount() {
        return amount;
    }

    public long getTimeToInt() {
        return timeToInt;
    }

    public String getDate() {
        return date;
    }

    @Override
    long getDateTime() {
        return this.timeToInt;
    }
}
