package co.example.hment.myapplication.ResultType;

/**
 * Created by hment on 5/9/2017.
 */

public class detail_item {
    public String account_name;
    public String type;
    public double num;
    public String in_or_out;

    public detail_item(){
    }

    public void setAccountName(String account_name){
        this.account_name = account_name;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setNum(Double num){
        this.num = num;
        if(num > 0){
            this.in_or_out = "income";
        }
        else
            this.in_or_out = "expense";
    }
}
