package co.example.hment.myapplication.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;

import co.example.hment.myapplication.DB.CRUD_account;
import co.example.hment.myapplication.DB.Table_account;
import co.example.hment.myapplication.R;
import co.example.hment.myapplication.Session;

public class AccountEditActivity extends EditBaseActivity<Table_account> {

    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_ACCOUNT_ID = "account_id";
    EditText editCardName;
    EditText editMoney;
    String CardName;
    double Money;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_edit;
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.account_edit_save).setVisibility(View.GONE);
        findViewById(R.id.account_edit_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(@NonNull final int account_id) {

//        MenuItem saveIcon= (MenuItem) findViewById(R.id.ic_save);
//        saveIcon.setVisible(false);

        final CRUD_account newAccounts = new CRUD_account(AccountEditActivity.this);
        final Table_account newAccount = newAccounts.getAccountById(account_id);

        DecimalFormat df = new DecimalFormat("###.00");
        String formatMoney = df.format(newAccount.money);

        ((EditText) findViewById(R.id.card_name_edit)).setText(newAccount.accountName);
        //        ((EditText) findViewById(R.id.cardholder_edit)).setText();
        ((EditText) findViewById(R.id.balance_edit)).setText(formatMoney);
        findViewById(R.id.account_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent resultIntent  = new Intent();
//                resultIntent.putExtra(KEY_ACCOUNT_ID, account_id);
//                setResult(Activity.RESULT_OK, resultIntent);
//                finish();
                newAccounts.deleteAccountByAid(account_id);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("frag", "accountFragment");
                startActivity(intent);
//                finish();
            }
        });

        findViewById(R.id.account_edit_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCardName = ((EditText) findViewById(R.id.card_name_edit)).getText().toString();
                double newBalance = Double.parseDouble(((EditText) findViewById(R.id.balance_edit)).getText().toString());
                newAccount.accountName = newCardName;
                newAccount.money = newBalance;
                newAccounts.updateAccount(newAccount);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("frag", "accountFragment");
                startActivity(intent);
                //finish();
            }
        });
    }

    @Override
    protected void saveAndExit(@Nullable Table_account newAccount) {
        if (newAccount == null) {
            newAccount= new Table_account();
        }

        editCardName = (EditText) findViewById(R.id.card_name_edit);
        editMoney = (EditText) findViewById(R.id.balance_edit);

        //Input check, users have to input both card name and balance
        CardName = editCardName.getText().toString();
        if (CardName == null || CardName.length() == 0) {
            editCardName.setError("Please input a card name!");
            return;
        }

        try {
            Money = Double.parseDouble(editMoney.getText().toString());
        } catch (Exception e) {
            editMoney.setError("Please input balance!");
            return;
        }

        newAccount.accountName = CardName;
        newAccount.money = Money;

        Session session = new Session(this);
        int uid = session.getUid();
        newAccount.uid = uid;
        //newAccount.uid = 1;

        CRUD_account newAccounts = new CRUD_account(AccountEditActivity.this);
        int newAccountId = newAccounts.insertAccount(newAccount);
        Log.d("orange", String.valueOf(newAccountId));

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("frag", "accountFragment");
        startActivity(intent);
//        finish();
    }

    @Override
    protected int initializeData() { //get the account id from intent
//        return getIntent().getParcelableExtra(KEY_ACCOUNT);
        return getIntent().getIntExtra(KEY_ACCOUNT, 0);
    }

}
