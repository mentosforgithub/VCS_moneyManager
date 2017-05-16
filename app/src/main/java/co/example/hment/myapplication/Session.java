package co.example.hment.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hment on 5/9/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(int uid){
        editor.putBoolean("loggedInmode",true);
        editor.putInt("uid",uid);
        editor.commit();
    }

    public int getUid(){
        int uid = prefs.getInt("uid",0);
        return uid;
    }

    public void setLoggedOut(){
        editor.putBoolean("loggedInmode", false);
        editor.putInt("uid", -1);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }
}
