package module;

import java.util.LinkedList;
import java.util.List;

public class OpenHistory extends History {

    private int customerId;
    private int accountId;
    private String date;
    private long dateToNumber;

    public int getAccountId() {
        return accountId;
    }

    public String getDate() {
        return date;
    }

    public int getCustomerId() {
        return customerId;
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

    public void setDateToNumber(long dateToNumber) {
        this.dateToNumber = dateToNumber;
    }

    @Override
    public long getDateTime() {
        return dateToNumber;
    }
}
