package co.example.hment.myapplication.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import co.example.hment.myapplication.DB.CRUD_transaction;
import co.example.hment.myapplication.R;

public class Stats extends Fragment {

    private static String TAG = "Stats-PieChart";
    //    private float[] spendingData = {0, 10.6f, 234f, 334.2f, 99.6f, 538.8f, 201.1f};
    private String[] categories = {"Food", "Traffic", "HealthCare", "Clothes", "Entertainment", "Education", "Other"};
    float[] spendingData;
    String[] spendingDataToString;
    PieChart pieChart;
    double outcome;
    double income;
    TextView outcomeView;
    TextView incomeView;

    public Stats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View statsView = inflater.inflate(R.layout.fragment_stats, container, false);

        spendingData = getSpendingData(categories);
        for (int i=0; i<spendingData.length; i++) {
            spendingData[i] = Math.abs(spendingData[i]);
        }

        //convert the format of data to "###.00"
        spendingDataToString = new String[spendingData.length];
        for (int i=0; i<spendingDataToString.length; i++) {
            DecimalFormat df = new DecimalFormat("###.00");
            spendingDataToString[i] = df.format(spendingData[i]);
        }

        //convert the formarted string data back to float
        for (int i=0; i<spendingData.length; i++) {
            spendingData[i] = Float.parseFloat(spendingDataToString[i]);
        }

        outcome = getOutcome();
        income = getIncome();
        DecimalFormat df = new DecimalFormat("###.00");
        String outcomeToString  = df.format(outcome);
        String incomeToString = df.format(income);
        outcomeView = (TextView) statsView.findViewById(R.id.stats_outcome_amount);
        incomeView = (TextView) statsView.findViewById(R.id.stats_intcome_amount);
        outcomeView.setText(outcomeToString);
        incomeView.setText(incomeToString);

        pieChart = (PieChart) statsView.findViewById(R.id.stats_pie_chart);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(true);
        pieChart.setCenterText("Spending");
        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected Value selected from chart");
                Log.d(TAG, e.toString());
                Log.d(TAG, h.toString());

                int pos1 = e.toString().indexOf("y:");
                String spending = e.toString().substring(pos1 + 3);

                for (int i = 0; i < spendingData.length; i++) {
                    if (spendingData[i] == Math.abs(Float.parseFloat(spending))) {
                        pos1 = i;
                        break;
                    }
                }
                String category = categories[pos1];
                Toast.makeText(getActivity(), category + ": $" + spending, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });



        // Inflate the layout for this fragment
        return statsView;
    }

    private float[] getSpendingData (String[] categories) {

        CRUD_transaction transaction = new CRUD_transaction(getActivity());
        float[] spendingData = new float[categories.length];
        for (int i=0; i<categories.length; i++) {
            double moneyType = transaction.getMoneyByType(categories[i]);
            spendingData[i] = (float) moneyType;
        }
        return spendingData;
    }

    private double getOutcome () {
        CRUD_transaction transaction = new CRUD_transaction(getActivity());
        double outcomeAll = transaction.getAllOutcome();
        return outcomeAll;
    }

    private double getIncome () {
        CRUD_transaction transaction = new CRUD_transaction(getActivity());
        double incomeAll = transaction.getAllIncome();
        return incomeAll;
    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");

        ArrayList<PieEntry> spendingEntry = new ArrayList<>();
        ArrayList<String> categoryEntry = new ArrayList<>();

        for (int i = 0; i < spendingData.length; i++) {
            spendingEntry.add(new PieEntry(spendingData[i], i));
            Log.d("banana", String.valueOf(spendingData[i]));
        }

        for (int i = 0; i < categories.length; i++) {
            categoryEntry.add(categories[i]);
        }

        //Create the data set
        PieDataSet pieDataSet = new PieDataSet(spendingEntry, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(239, 176, 98)); //Food
        colors.add(Color.rgb(167, 247, 140)); //Traffic
        colors.add(Color.rgb(112, 165, 249)); //HealthCare
        colors.add(Color.rgb(247, 140, 156)); //Clothes
        colors.add(Color.rgb(206, 155, 219)); //Entertainment
        colors.add(Color.rgb(80, 107, 107)); //Education
        colors.add(Color.rgb(190, 196, 196)); //Other
        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setUsePercentValues(true);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

}
