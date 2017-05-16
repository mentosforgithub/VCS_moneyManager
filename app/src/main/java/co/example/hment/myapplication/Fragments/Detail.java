package co.example.hment.myapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.example.hment.myapplication.DB.CRUD_transaction;
import co.example.hment.myapplication.AdapterHelper.DetailAdapter;
import co.example.hment.myapplication.R;
import co.example.hment.myapplication.ResultType.detail_item;
import co.example.hment.myapplication.Session;

public class Detail extends Fragment {

    private void  setupUI(@NonNull List<detail_item> detail_items){
        ListView listview = (ListView)getView().findViewById(R.id.list);
        listview.setAdapter(new DetailAdapter(getActivity(),detail_items));
    }
    public Detail() {
    }

    private void detailList(){
    }

    public void goRecord(View view){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView t1 = (TextView)view.findViewById(R.id.test1);

        //get uid
        Session session = new Session(getContext());
        int uid = session.getUid();


        CRUD_transaction trans = new CRUD_transaction(getContext());
        ArrayList<detail_item> detailslist = new ArrayList<>();
        detailslist = trans.getDetailsList(uid);
        ListView listview = (ListView)view.findViewById(R.id.list);
        listview.setAdapter(new DetailAdapter(getActivity(),detailslist));


        return view;
    }

}
