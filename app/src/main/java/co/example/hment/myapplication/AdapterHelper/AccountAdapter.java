package co.example.hment.myapplication.AdapterHelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import co.example.hment.myapplication.Activities.AccountEditActivity;
import co.example.hment.myapplication.R;
import co.example.hment.myapplication.ResultType.AccountList;

/**
 * Created by hment on 5/9/2017.
 */

public class AccountAdapter extends ArrayAdapter<AccountList> {

    public AccountAdapter(@NonNull Context context, ArrayList<AccountList> accounts) {
        super(context, 0, accounts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final AccountList accountList = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_item, parent, false);
        }

        // Lookup view for data population
        TextView cardId = (TextView) convertView.findViewById(R.id.account_id);
        TextView cardname = (TextView) convertView.findViewById(R.id.card_name);
        TextView money = (TextView) convertView.findViewById(R.id.balance);

        // Populate the data into the template view using the data object
        cardId.setText(String.valueOf(accountList.cardId));
        cardname.setText(accountList.cardName);

        DecimalFormat df = new DecimalFormat("###.00");
        String formatMoney = df.format(accountList.money);
        money.setText(formatMoney);

        ImageButton editAccount = (ImageButton) convertView.findViewById(R.id.edit_account_btn);
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AccountEditActivity.class);
                //put the account id to intent
                intent.putExtra(AccountEditActivity.KEY_ACCOUNT, accountList.cardId);
                getContext().startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;

    }
}
