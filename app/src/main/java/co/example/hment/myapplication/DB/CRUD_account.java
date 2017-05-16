package co.example.hment.myapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import co.example.hment.myapplication.ResultType.AccountList;
import co.example.hment.myapplication.AdapterHelper.Account_Spinner;

/**
 * Created by hment on 5/9/2017.
 */

public class CRUD_account {
    private DBHelper2 dbHelper;

    public CRUD_account(Context context){
        dbHelper=new DBHelper2(context);
    }

    //insert
    public int insertAccount (Table_account account) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues accountValues = new ContentValues();
        accountValues.put(Table_account.KEY_Aname, account.accountName);
        accountValues.put(Table_account.KEY_money, account.money);
        accountValues.put(Table_account.KEY_Uid, account.uid);

        long aid = db.insert(Table_account.TABLE, null, accountValues);
        db.close();
        return (int) aid;
    }

    //select - get Spinner
    public ArrayList<Account_Spinner> getAccountsList(int uid){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectAccounts = "SELECT * " + " FROM "
                + Table_account.TABLE + " WHERE " + Table_account.KEY_Uid + " =?";
        ArrayList<Account_Spinner> accountsList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectAccounts, new String[] {String.valueOf(uid)});
        if(cursor.moveToFirst()){
            do{
                Account_Spinner aSpinner = new Account_Spinner();
                aSpinner.setAccountID(cursor.getInt(cursor.getColumnIndex(Table_account.KEY_Aid)));
                aSpinner.setAccountName(cursor.getString(cursor.getColumnIndex(Table_account.KEY_Aname)));
                aSpinner.setMoney(cursor.getDouble(cursor.getColumnIndex(Table_account.KEY_money)));
                accountsList.add(aSpinner);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountsList;
    }

    //delete from account table by account id
    public void deleteAccountByAid (int account_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Table_account.TABLE, Table_account.KEY_Aid + "=?", new String[]{String.valueOf(account_id)});
        db.close();
    }

    //delete from account table by account name
    public void deleteAccountByAname (String account_name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Table_account.TABLE, Table_account.KEY_Aname + "=?", new String[]{account_name});
        db.close();
    }

    //edit account
    public void updateAccount (Table_account account) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues accountValues = new ContentValues();
        accountValues.put(Table_account.KEY_Aid, account.accountID);
        accountValues.put(Table_account.KEY_Aname, account.accountName);
        accountValues.put(Table_account.KEY_money, account.money);
        accountValues.put(Table_account.KEY_Uid, account.uid);

        db.update(Table_account.TABLE, accountValues, Table_account.KEY_Aid + "=?", new String[]{String.valueOf(account.accountID)});
        db.close();
    }

    //get account list
    public ArrayList<HashMap<String, String>> getAccountList () {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "
                + Table_account.TABLE + "." + Table_account.KEY_Aid + ","
                + Table_account.TABLE + "." + Table_account.KEY_Aname + ","
                + Table_account.TABLE + "." + Table_account.KEY_money + ","
                + Table_account.TABLE + "." + Table_account.KEY_Uid + ","
                + Table_UserInfo.TABLE + "." + Table_UserInfo.KEY_Uname
                + " FROM "
                + Table_account.TABLE + ","
                + Table_UserInfo.TABLE
                + " WHERE "
                + Table_account.TABLE + "." + Table_account.KEY_Uid
                + " = " + Table_UserInfo.TABLE + "." + Table_UserInfo.KEY_uid;

        ArrayList<HashMap<String, String>> accountList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> account = new HashMap<>();
                account.put("accountId", cursor.getString(cursor.getColumnIndex(Table_account.KEY_Aid)));
                account.put("accountName", cursor.getString(cursor.getColumnIndex(Table_account.KEY_Aname)));
                account.put("money", cursor.getString(cursor.getColumnIndex(Table_account.KEY_money)));
                account.put("userID", cursor.getString((cursor.getColumnIndex(Table_account.KEY_Uid))));
                account.put("userName", cursor.getString(cursor.getColumnIndex(Table_UserInfo.KEY_Uname)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountList;
    }

    //get account list, not use user info, using ResultType AccountList
    public ArrayList<AccountList> getAccountList2 (int uid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + Table_account.TABLE + " WHERE " + Table_transaction.KEY_Uid + "=" +uid;
        ArrayList<AccountList> accountList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AccountList account = new AccountList(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(Table_account.KEY_Aid)).toString()),
                        cursor.getString(cursor.getColumnIndex(Table_account.KEY_Aname)).toString(),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex(Table_account.KEY_money)).toString()));
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountList;
    }

    public Table_account getAccountById(int aid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT *"
//                + Table_account.KEY_Aname + ","
//                + Table_account.KEY_money
                + " FROM " + Table_account.TABLE
                + " WHERE " + Table_account.KEY_Aid + "=?";
        int iCount = 0;
        Table_account account = new Table_account();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(aid)});
        if (cursor.moveToFirst()) {
            do {
                account.accountID = cursor.getInt(cursor.getColumnIndex(Table_account.KEY_Aid));
                account.accountName = cursor.getString(cursor.getColumnIndex(Table_account.KEY_Aname));
                account.money = cursor.getDouble(cursor.getColumnIndex(Table_account.KEY_money));
                account.uid = cursor.getInt(cursor.getColumnIndex(Table_account.KEY_Uid));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return account;
    }
}

