package co.example.hment.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.example.hment.myapplication.AdapterHelper.AccountAdapter;
import co.example.hment.myapplication.Activities.AccountEditActivity;
import co.example.hment.myapplication.DB.CRUD_account;
import co.example.hment.myapplication.R;
import co.example.hment.myapplication.ResultType.AccountList;
import co.example.hment.myapplication.Session;

public class Account extends Fragment {

    private static final int REQ_CODE_EDIT_ACCOUNT = 100;
    private ImageButton addAccountBtn;
    ArrayList<AccountList> accountLists;
    AccountAdapter aAdapter;

    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View accountView = inflater.inflate(R.layout.fragment_account, container, false);

        //Click "+" button, go to account edit page
        addAccountBtn = (ImageButton) accountView.findViewById(R.id.add_account);
        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AccountEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_ACCOUNT);
            }
        });

        setUpAccounts(accountView);

        // Inflate the layout for this fragment
        return accountView;
    }

    private void setUpAccounts(View accountView) {
        Session session = new Session(getContext());
        int uid = session.getUid();

        CRUD_account setUpAccount = new CRUD_account(getActivity());
        accountLists = setUpAccount.getAccountList2(uid);
        aAdapter = new AccountAdapter(getActivity(), accountLists);
        ListView lv = (ListView) accountView.findViewById(R.id.account_list);
        aAdapter.notifyDataSetChanged();
        if (accountLists.size() != 0) {
            lv.setAdapter(aAdapter);
        } else {
            Toast.makeText(getActivity(), "No accounts!", Toast.LENGTH_LONG);
        }

    }

}
