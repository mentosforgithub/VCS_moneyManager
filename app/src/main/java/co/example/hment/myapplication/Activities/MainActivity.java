package co.example.hment.myapplication.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import co.example.hment.myapplication.Fragments.Account;
import co.example.hment.myapplication.Fragments.Detail;
import co.example.hment.myapplication.Fragments.Home;
import co.example.hment.myapplication.Fragments.Stats;
import co.example.hment.myapplication.R;
import co.example.hment.myapplication.Session;

public class MainActivity extends AppCompatActivity {

    //    private MenuItem btnLogout;
    private Button btnLogout;
    private Session session;

    AHBottomNavigation bottomNavigation;
    AHBottomNavigationItem menu1;
    AHBottomNavigationItem menu2;
    AHBottomNavigationItem menu3;
    AHBottomNavigationItem menu4;
    FragmentManager fragmentManager;
    String frag;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.logout_icon) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Intent intent = getIntent();
            frag = intent.getExtras().getString("frag");
            if (frag.equals("accountFragment")) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout,new Account(),"Account")
                        .commit();
            }
            if(frag.equals("logToHome")){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout,new Home(),"Home")
                        .commit();
            }
        } catch (Exception e) {
            Log.d("vegetable", "chucuola!" + frag);
        }


        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }

//        btnLogout = (MenuItem) findViewById(R.id.btnLogout);
//
//        btnLogout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                logout();
//                return true;
//            }
//        });

        //btnLogout = (Button) findViewById(R.id.btnLogout);
        /*btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });*/
//

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_bar);
        menu1 = new AHBottomNavigationItem(getString(R.string.home),R.drawable.ic_account_balance_black_24dp);
        menu2 = new AHBottomNavigationItem(getString(R.string.detail),R.drawable.ic_format_list_bulleted_black_24dp);
        menu3 = new AHBottomNavigationItem(getString(R.string.stats),R.drawable.ic_equalizer_black_24dp);
        menu4 = new AHBottomNavigationItem(getString(R.string.account),R.drawable.ic_person_black_24dp);

        bottomNavigation.addItem(menu1);
        bottomNavigation.addItem(menu2);
        bottomNavigation.addItem(menu3);
        bottomNavigation.addItem(menu4);

        bottomNavigation.setDefaultBackgroundColor(Color.DKGRAY);
        bottomNavigation.setAccentColor(Color.WHITE);
        bottomNavigation.setInactiveColor(Color.LTGRAY);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener(){
            public boolean onTabSelected(int position,  boolean wasSelected){
                switch (position){
                    case 0:
                        Home home = new Home();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout,home,"Home")
                                .commit();
                        break;
                    case 1:
                        Detail detail = new Detail();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout,detail,"Detail")
                                .commit();
                        break;
                    case 2:
                        Stats stats = new Stats();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout,stats,"Stats")
                                .commit();
                        break;
                    case 3:
                        Account account = new Account();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout,account,"Account")
                                .commit();
                        break;
                }

                return true;
            }
        });

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        Intent intent = getIntent();
//        String frag = intent.getExtras().getString("frag");
//
//        switch (frag) {
//            case "accountFragment":
//                fragmentManager.beginTransaction().replace(R.id.frame_layout, new Account()).commit();
//        }
//    }

    private void logout(){
        session.setLoggedOut();
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        Log.v("not ","intent");
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.btnLogout) {
//            logout();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
