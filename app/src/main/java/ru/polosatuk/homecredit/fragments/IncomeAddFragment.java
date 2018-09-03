package ru.polosatuk.homecredit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.polosatuk.homecredit.R;
import ru.polosatuk.homecredit.observerDateTime.Observer;
import ru.polosatuk.homecredit.observerDateTime.TimeDateFactory;

public class IncomeAddFragment extends Fragment implements View.OnClickListener, Observer{
    private Button button;
    private TimeDateFactory timeDateFactory;
    private Button btnPlusDate;
    private TextView tvTime;
    private TextView tvDate;
    private Button btnMinusDate;
    String date = "пусто", time = "пусто";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incomeadd, container, false);
        timeDateFactory = new TimeDateFactory(view.getContext(), this);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        btnMinusDate = (Button) view.findViewById(R.id.btnMinusDate);
        btnPlusDate = (Button) view.findViewById(R.id.btnPlusDate);

        btnPlusDate.setOnClickListener(this);
        btnMinusDate.setOnClickListener(this);

        timeDateFactory.setDateTime(date, time);
        return view;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"Fragment 2 button pressed",Toast.LENGTH_LONG).show();
        switch (v.getId()){

        case R.id.btnMinusDate:
        timeDateFactory.minusDate(date);
        break;
        case R.id.btnPlusDate:
        timeDateFactory.plusDate(date);
        break;
    }
    }

    @Override
    public void update(String date, String time) {
        this.date = date;
        tvDate.setText(date);
        tvTime.setText(time);
    }
}
