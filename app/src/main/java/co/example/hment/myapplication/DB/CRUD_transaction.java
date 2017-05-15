package co.example.hment.myapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.example.hment.myapplication.ResultType.detail_item;

/**
 * Created by hment on 5/9/2017.
 */

public class CRUD_transaction {
    private DBHelper2 dbHelper;

    public CRUD_transaction(Context context){
        dbHelper=new DBHelper2(context);
    }

    //insert
    public int insertTransaction (Table_transaction newTran) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues transactionValues = new ContentValues();
        transactionValues.put(Table_transaction.KEY_Type, newTran.type);
        transactionValues.put(Table_transaction.KEY_time, newTran.time);
        transactionValues.put(Table_transaction.KEY_num, newTran.number);
        transactionValues.put(Table_transaction.KEY_Aid, newTran.aid);
        transactionValues.put(Table_transaction.KEY_Uid, newTran.uid);
        long tid = db.insert(Table_transaction.TABLE, null, transactionValues);

        //getAccountBalance
        double balance = 20.0;
        String getBalance = "SELECT " + Table_account.KEY_money +
                " FROM " + Table_account.TABLE +
                " WHERE " + Table_account.KEY_Aid + " = " + newTran.aid;
        Cursor cursor = db.rawQuery(getBalance, null);
        if(cursor.moveToFirst()){
            do{
                balance = cursor.getDouble(cursor.getColumnIndex(Table_account.KEY_money));
            }while(cursor.moveToNext());
        }
        cursor.close();

        //update account
        ContentValues updateAccount = new ContentValues();
        updateAccount.put(Table_account.KEY_money,balance + newTran.number);
        db.update(Table_account.TABLE,updateAccount,Table_account.KEY_Aid + "=?",new String[]{String.valueOf(newTran.aid)});

        db.close();
        return (int) tid;
    }
    //delete
    public void delete(int tid){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(Table_transaction.TABLE, Table_transaction.KEY_Tid + " =?",new String[] {String.valueOf(tid)});
        db.close();
    }
    //select - day
    public String getDay(int tid){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectDay = "SELECT " + Table_transaction.KEY_time +
                " FROM " + Table_transaction.TABLE + " WHERE " +
                Table_transaction.KEY_Tid + " =? ";
        Cursor cursor = db.rawQuery(selectDay, new String[] {String.valueOf(tid)});
        String result = "NOT INITIALIZED";
        if(cursor.moveToFirst()){
            do{
                //Double temp = cursor.getDouble(cursor.getColumnIndex(Table_transaction.KEY_num));
                //result = Double.toString(temp);
                result = cursor.getString(cursor.getColumnIndex(Table_transaction.KEY_time));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }
    //select - transaction details
    public ArrayList<detail_item> getDetailsList(int uid){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectDetails = "SELECT " +
                Table_account.TABLE + "." + Table_account.KEY_Aname + " AS aname, " +
                Table_transaction.TABLE + "." + Table_transaction.KEY_Type + " AS type, " +
                Table_transaction.TABLE + "." + Table_transaction.KEY_num
                + " AS num FROM " + Table_account.TABLE + ", "
                + Table_transaction.TABLE + " WHERE " +
                Table_account.TABLE + "." + Table_account.KEY_Uid +
                " = " + Table_transaction.TABLE + "." + Table_transaction.KEY_Uid + " AND " +
                Table_transaction.TABLE + "." + Table_transaction.KEY_Aid  + "=" + Table_account.TABLE + "." +Table_account.KEY_Aid +
                " AND "
                + Table_account.TABLE + "." + Table_account.KEY_Uid + " = ?";
        ArrayList<detail_item> detailsList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectDetails, new String[] {String.valueOf(uid)});
        if(cursor.moveToFirst()){
            do{
                detail_item detail = new detail_item();
                detail.setAccountName(cursor.getString(cursor.getColumnIndex("aname")));
                detail.setType(cursor.getString(cursor.getColumnIndex("type")));
                detail.setNum(cursor.getDouble(cursor.getColumnIndex("num")));
                detailsList.add(detail);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return detailsList;
    }
    //select - test
    public String getDayFromJoint (int tid){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectTest = "SELECT " + Table_account.TABLE + "." + Table_account.KEY_Aname +
                " FROM " + Table_transaction.TABLE + " ," + Table_account.TABLE +
                " WHERE " + Table_account.TABLE + "." + Table_account.KEY_Uid +
                " = " + Table_transaction.TABLE + "." + Table_transaction.KEY_Uid + " AND "
                + Table_transaction.TABLE + "." + Table_transaction.KEY_Tid + " = ?";
        Cursor cursor = db.rawQuery(selectTest, new String[] {String.valueOf(tid)});
        String result = "NOT INITIALIZED";
        if(cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndex(Table_account.TABLE + "." + Table_account.KEY_Aname));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    // get the spending amount by type
    public double getMoneyByType (String type){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        double typeSum = 0;
        String query = "SELECT " + Table_transaction.KEY_num + " FROM " + Table_transaction.TABLE + " WHERE "
                + Table_transaction.KEY_num + "<0 and "
                + Table_transaction.KEY_Type + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{type});
        if (cursor.moveToFirst()) {
            do {
                typeSum += cursor.getDouble(cursor.getColumnIndex(Table_transaction.KEY_num));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return typeSum;
    }

    //get the speding amount by month
    //to be implemented
    public double getOutcomeByMonth (String date) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        double monthOutcome = 0;
        String query = "SELECT " + Table_transaction.KEY_num + " FROM " + Table_transaction.TABLE + " WHERE ";

        return monthOutcome;
    }

    //get the income amount by month
    //to be implemented
    public double getIncomeByMonth (String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        double monthIncome = 0;

        return monthIncome;
    }

    //get all outcome
    public double getAllOutcome () {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        double outcome = 0;
        String query = "SELECT " + Table_transaction.KEY_num + " FROM " + Table_transaction.TABLE + " WHERE "
                + Table_transaction.KEY_num + "<0";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                outcome += cursor.getDouble(cursor.getColumnIndex(Table_transaction.KEY_num));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return outcome;
    }

    //get all income
    public double getAllIncome() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        double income = 0;
        String query = "SELECT " + Table_transaction.KEY_num + " FROM " + Table_transaction.TABLE + " WHERE "
                + Table_transaction.KEY_num + ">0";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                income += cursor.getDouble(cursor.getColumnIndex(Table_transaction.KEY_num));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return income;
    }
}
