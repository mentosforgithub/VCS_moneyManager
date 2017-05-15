package co.example.hment.myapplication.DB;

/**
 * Created by hment on 5/9/2017.
 */

public class Table_account {
    //table
    public  static  final String TABLE="account";
    //domain
    public static final  String KEY_Aid = "aid";
    public static final  String KEY_Aname = "accountName";
    public static final  String KEY_money = "money";
    public static final  String KEY_Uid = "uid";
    //attributes
    public int accountID;
    public String accountName;
    public double money;
    public int uid;
}
