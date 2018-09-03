package ru.polosatuk.homecredit;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;


public class SpinnerSimpleAdapter {
   private Context context;
    private ArrayList list;
        public SpinnerSimpleAdapter(Context context, ArrayList list){
            this.context = context;
            this.list = list;
    }

    public SpinnerAdapter getSpinnerAdapter(){
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, list);
    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    return adapter;
}
}
