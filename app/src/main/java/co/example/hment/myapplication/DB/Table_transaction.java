package co.example.hment.myapplication.DB;

/**
 * Created by hment on 5/9/2017.
 */

public class Table_transaction {
    //table
    public  static  final String TABLE="transaction_table";
    //domain
    public static final  String KEY_Tid = "transactionID";
    public static final  String KEY_Type = "type";
    public static final  String KEY_num = "number";
    public static final  String KEY_time = "time";
    public static final  String KEY_Uid = "uid";
    public static final  String KEY_Aid = "aid";
    //attributes
    public int transactionID;
    public String type;
    public double number;
    public String time;
    public int uid;
    public int aid;
}
