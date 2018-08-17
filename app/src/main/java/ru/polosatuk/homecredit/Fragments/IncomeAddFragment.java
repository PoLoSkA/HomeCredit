package ru.polosatuk.homecredit.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ru.polosatuk.homecredit.R;

public class IncomeAddFragment extends Fragment implements View.OnClickListener{
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incomeadd, container, false);

        return view;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"Fragment 2 button pressed",Toast.LENGTH_LONG).show();

    }
}
