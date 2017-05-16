package co.example.hment.myapplication.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hment on 5/9/2017.
 */

public class DBHelper2 extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=4;

    private static final String DATABASE_NAME="money_manager.db";

    public DBHelper2(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        String CREATE_TABLE_USERINFO="CREATE TABLE "+ Table_UserInfo.TABLE+"("
                +Table_UserInfo.KEY_uid+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +Table_UserInfo.KEY_Uname + " TEXT NOT NULL, "
                +Table_UserInfo.KEY_Password + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_USERINFO);

        String CREATE_TABLE_ACCOUNT="CREATE TABLE " + Table_account.TABLE +"("
                +Table_account.KEY_Aid +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +Table_account.KEY_Aname + " TEXT NOT NULL,"
                +Table_account.KEY_money + " DOUBLE,"
                +Table_account.KEY_Uid + " INTEGER,"
                + " FOREIGN KEY ("+Table_account.KEY_Uid+") REFERENCES "+Table_UserInfo.TABLE+"("+Table_UserInfo.KEY_uid+"))";
        db.execSQL(CREATE_TABLE_ACCOUNT);

        String CREATE_TABLE_TRANSACTION="CREATE TABLE " + Table_transaction.TABLE + " ("
                +Table_transaction.KEY_Tid +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +Table_transaction.KEY_num + " DOUBLE,"
                +Table_transaction.KEY_time + " TEXT NOT NULL,"
                +Table_transaction.KEY_Type + " TEXT NOT NULL,"
                +Table_transaction.KEY_Uid + " INTEGER,"
                +Table_transaction.KEY_Aid + " INTEGER, "
                + " FOREIGN KEY ("+Table_transaction.KEY_Aid+") REFERENCES "+Table_account.TABLE+"("+Table_account.KEY_Aid+"),"
                + " FOREIGN KEY ("+Table_transaction.KEY_Uid+") REFERENCES "+Table_UserInfo.TABLE+"("+Table_UserInfo.KEY_uid+"))";
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete if already existed
        db.execSQL("DROP TABLE IF EXISTS "+ Table_UserInfo.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_account.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_transaction.TABLE);
        //create again
        onCreate(db);
    }
}
