package co.example.hment.myapplication.ResultType;

/**
 * Created by hment on 5/9/2017.
 */

public class AccountList {

    public int cardId;
    public String cardName;
    public double money;


    public AccountList (int cardId, String cardName, double money) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.money = money;
    }

}
