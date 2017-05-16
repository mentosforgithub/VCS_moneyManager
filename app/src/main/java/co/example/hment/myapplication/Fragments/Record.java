package co.example.hment.myapplication.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import co.example.hment.myapplication.DB.CRUD_account;
import co.example.hment.myapplication.DB.CRUD_transaction;
import co.example.hment.myapplication.DB.Table_transaction;
import co.example.hment.myapplication.R;
import co.example.hment.myapplication.Session;
import co.example.hment.myapplication.AdapterHelper.Account_Spinner;


public class Record extends Fragment {
    Double num;
    String type;
    int uid;
    String account_name;
    int aid;
    String day;

    boolean nega = false;
    RadioButton expenseBtn, incomeBtn;
    TextView testCur;

    String targetCur = "USD";
    String API_URL = "http://free.currencyconverterapi.com/api/v3/convert";
    Double rate = 1.0;

    ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> currencies = new ArrayList<>();


    public Record() {
        // Required empty public constructor
    }

    class retrieveRates extends AsyncTask<String, Void, String> {

        private Exception exception;
        private Double rate;

        protected void onPreExecute() {

        }

        protected String doInBackground(String...args) {

            try {
                targetCur = args[0];
                URL url = new URL(API_URL + "?q=USD_" + targetCur + "&compact=y");
                Log.v("url",url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    Log.v("gson",stringBuilder.toString());
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            Gson gson = new Gson();
            Log.v("string",response);
            Type mapType = new TypeToken<Map<String,Map<String, String>>>() {}.getType();
            Map<String,Map<String, String>> map = gson.fromJson(response, mapType);
            this.rate = Double.parseDouble(map.get("USD_" + targetCur).get("val"));;
            Log.v("rate",this.rate.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        final TextView ttt = (TextView)view.findViewById(R.id.toTest);
        final TextView show = (TextView)view.findViewById(R.id.show_test);
        testCur = (TextView) view.findViewById(R.id.hide_cur);
        testCur.setVisibility(TextView.INVISIBLE);
        show.setVisibility(TextView.INVISIBLE);
        ttt.setVisibility(TextView.INVISIBLE);

        //get uid
        Session session = new Session(getContext());
        uid = session.getUid();

        //get values
        final Spinner currencySpinner = (Spinner)view.findViewById(R.id.currency_spinner);
        currencies.add("USD($)");
        currencies.add("CNY(¥)");
        currencies.add("ECU(€)");
        currencies.add("JPY(¥)");
        currencies.add("GBP(£)");
        ArrayAdapter<String> curSpinnerAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner, currencies);
        currencySpinner.setAdapter(curSpinnerAdapter);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                testCur.setText(currencySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final EditText text_num = (EditText) view.findViewById(R.id.record_money);
        num = Double.parseDouble(text_num.getText().toString());


        expenseBtn = (RadioButton) view.findViewById(R.id.expense_btn);
        incomeBtn = (RadioButton) view.findViewById(R.id.income_btn);

        final Spinner spin_cate = (Spinner)view.findViewById(R.id.record_spinner);
        //type = spin_cate.getSelectedItem().toString();
        categories.add("Food");
        categories.add("Traffic");
        categories.add("HealthCare");
        categories.add("Clothes");
        categories.add("Entertainment");
        categories.add("Education");
        categories.add("Other");
        ArrayAdapter<String> cateSpinnerAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner, categories);
        spin_cate.setAdapter(cateSpinnerAdapter);
        spin_cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ttt.setText(spin_cate.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CRUD_account accountCRUD = new CRUD_account(getContext());
        ArrayList<Account_Spinner> accountsList = accountCRUD.getAccountsList(uid);
        ArrayAdapter<Account_Spinner> accountSpinnerAdapter = new ArrayAdapter<Account_Spinner>(getContext(),R.layout.spinner, accountsList);
        final Spinner spin_accont = (Spinner)view.findViewById(R.id.account_spinner_record);
        spin_accont.setAdapter(accountSpinnerAdapter);
        //Account_Spinner getAcc = (Account_Spinner) spin_accont.getSelectedItem();
        spin_accont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Account_Spinner getAid = (Account_Spinner)spin_accont.getSelectedItem();
                show.setText(Integer.toString(getAid.getAccountID()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //aid = getAcc.getAccountID();

        Date now = new Date();
        day = now.toString();


        //save Record and back to Home
        Button saveR = (Button)view.findViewById(R.id.save_record);
        saveR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(incomeBtn.isChecked()) {
                    type = "";
                }
                if(expenseBtn.isChecked()){
                    nega = true;
                }

                String currency = testCur.getText().toString();
                num = Double.parseDouble(text_num.getText().toString());
                num = nega? num * (-1) : num;
                switch (currency){
                    case "CNY(¥)":
                        targetCur = "CNY";
                        break;
                    case "ECU(€)":
                        targetCur = "EUR";
                        break;
                    case "JPY(¥)":
                        targetCur = "JPY";
                        break;
                    case "GBP(£)":
                        targetCur = "GBP";
                        break;
                    default:
                        targetCur = "USD";
                }

                retrieveRates exe = new retrieveRates();
                try{
                    String result = exe.execute(targetCur).get();
                    Gson gson = new Gson();
                    Type mapType = new TypeToken<Map<String,Map<String, String>>>() {}.getType();
                    Map<String,Map<String, String>> map = gson.fromJson(result, mapType);
                    rate = Double.parseDouble(map.get("USD_" + targetCur).get("val"));;
                    num = num / rate;
                }catch (Exception e){
                    Log.e("error",e.toString());
                }

                type = (String)ttt.getText();

                aid = Integer.parseInt(show.getText().toString());

                Table_transaction newRecord = new Table_transaction();
                newRecord.number = num;
                newRecord.type = type;
                newRecord.time = day;
                newRecord.uid = uid;
                newRecord.aid = aid;

                if(newRecord.number != 0.0){
                    CRUD_transaction newTran = new CRUD_transaction(getContext());
                    int tid = newTran.insertTransaction(newRecord);
                }

                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_layout,new Home())
                        .commit();
            }
        });
        //cancel Record and back to Home
        Button cancelR = (Button)view.findViewById(R.id.cancel_record);
        cancelR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_layout,new Home())
                        .commit();
            }
        });
        return view;
    }

}
