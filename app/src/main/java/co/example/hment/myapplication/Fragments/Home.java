package co.example.hment.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import co.example.hment.myapplication.DB.CRUD_account;
import co.example.hment.myapplication.R;
import co.example.hment.myapplication.Session;
import co.example.hment.myapplication.AdapterHelper.Account_Spinner;

public class Home extends Fragment {
    TextView tes;
    public Home() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tes = (TextView)view.findViewById(R.id.home_money);
        //final TextView showA = (TextView)view.findViewById(R.id.show_homespinner);
        //showA.setVisibility(TextView.INVISIBLE);

        Session session = new Session(getContext());
        int uid = session.getUid();

        /*mock user data
        Table_UserInfo mockUser = new Table_UserInfo();
        mockUser.userName = "mock1";
        mockUser.password ="mmm";


        CRUD_user try_sql = new CRUD_user(getContext());
        int uid = try_sql.insertUserInfo(mockUser);

        Table_UserInfo mockBack = try_sql.getUserById(uid);
        String mockUserName = mockBack.userName;*/

        /*mock account_data
        Table_account mockAccount = new Table_account();
        mockAccount.accountName = "PNC";
        mockAccount.money = 12.11;
        mockAccount.uid = uid;*/


        CRUD_account accountCRUD = new CRUD_account(getContext());
        //int aid = accountCRUD.insertAccount(mockAccount);
        ArrayList<Account_Spinner> accountsList = accountCRUD.getAccountsList(uid);
        ArrayAdapter<Account_Spinner> accountSpinnerAdapter = new ArrayAdapter<Account_Spinner>(getContext(),R.layout.spinner, accountsList);
        final Spinner spin_accont = (Spinner)view.findViewById(R.id.spinner_home);
        spin_accont.setAdapter(accountSpinnerAdapter);
        //Account_Spinner getAcc = (Account_Spinner) spin_accont.getSelectedItem();
        spin_accont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Account_Spinner getAcc = (Account_Spinner)spin_accont.getSelectedItem();
                if (getAcc == null) {
                    tes.setText("0.00");
                } else {
                    DecimalFormat df = new DecimalFormat("###.00");
                    df.format(getAcc.getMoney());
                    tes.setText(df.format(getAcc.getMoney()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //write uid to shared preference
        /*SharedPreferences uidSettings = getContext().getSharedPreferences("uidSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = uidSettings.edit();
        editor.putInt("uid",uid);
        editor.apply();*/

        Button record_btn = (Button)view.findViewById(R.id.record);
        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_layout,new Record())
                        .commit();
            }
        });

        return view;
    }

}
