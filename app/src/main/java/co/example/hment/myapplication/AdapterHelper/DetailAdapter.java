package co.example.hment.myapplication.AdapterHelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import co.example.hment.myapplication.R;
import co.example.hment.myapplication.ResultType.detail_item;

/**
 * Created by hment on 5/9/2017.
 */

public class DetailAdapter extends BaseAdapter {
    private Context context;
    private List<detail_item> data;


    public DetailAdapter(@NonNull Context context, @NonNull List<detail_item> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_item,null);
        detail_item detail = data.get(position);


        TextView test = (TextView)view.findViewById(R.id.account_name);
        ((TextView)view.findViewById(R.id.account_name)).setText(detail.account_name);
        ((TextView)view.findViewById(R.id.type)).setText(detail.type);
        ((TextView)view.findViewById(R.id.income_or_expense)).setText(detail.in_or_out);
        DecimalFormat df = new DecimalFormat("###.00");
        ((TextView)view.findViewById(R.id.num)).setText(df.format(detail.num));

        return view;
    }
}
