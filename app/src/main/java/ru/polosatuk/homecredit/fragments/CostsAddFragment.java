package ru.polosatuk.homecredit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.polosatuk.homecredit.BudgetDataContainer;
import ru.polosatuk.homecredit.ChoseDate;
import ru.polosatuk.homecredit.R;
import ru.polosatuk.homecredit.SpinnerSimpleAdapter;
import ru.polosatuk.homecredit.dataTable.DataBase;
import ru.polosatuk.homecredit.dataTable.DataBaseContract;
import ru.polosatuk.homecredit.observerDateTime.Observer;
import ru.polosatuk.homecredit.observerDateTime.TimeDateFactory;


public class CostsAddFragment extends Fragment implements View.OnClickListener, Observer {

    public static final String TAG = "CostsFragment";
    TextView vGetSalary; //тестовый вывод на экран УДАЛИТЬ
    TextView tvDate; // вывод даты на экран
    TextView tvTime;
    EditText vCostsAdd; // ввод потраченной суммы
    EditText vCommentOUT; // комментарий к потраченной сумме
    Button btnSave, btnGet, btnPlusDate, btnMinusDate;
    DataBase db;
    Spinner spCategory, spMoneyBox, spMoneyType;
    Boolean oneTimeStart = true;
    TimeDateFactory timeDateFactory;
    String date = "пусто", time = "пусто";

//    {   @Override
//    public void onPause() {
//        super.onPause();
//        Log.d(TAG, "----onPause----");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d(TAG, "----onResume----");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.d(TAG, "----onStop----");
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.d(TAG, "----onDetach----");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d(TAG, "----onStart----");
//    }}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "---OnCreateView---");
        View view = inflater.inflate(R.layout.fragment_costsadd, container, false);

        //инициализация времени
        timeDateFactory = new TimeDateFactory(view.getContext(), this);
        Log.d(TAG, "TimeDate = " + timeDateFactory.toString());
//        timeDateFactory.registerObserver(this);
        Log.d(TAG, this.toString());

        vCostsAdd = (EditText) view.findViewById(R.id.vCostsAdd);
        vCommentOUT = (EditText) view.findViewById(R.id.vCommentOUT);
        vGetSalary = (TextView) view.findViewById(R.id.salaryView);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvDate.setOnClickListener(this);

        btnGet = (Button) view.findViewById(R.id.btnGetSalary);
        btnSave = (Button) view.findViewById(R.id.btnSaveOUT);
        btnMinusDate = (Button) view.findViewById(R.id.btnMinusDate);
        btnPlusDate = (Button) view.findViewById(R.id.btnPlusDate);

        btnGet.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnPlusDate.setOnClickListener(this);
        btnMinusDate.setOnClickListener(this);

        if (oneTimeStart) {
            db = new DataBase(view.getContext());
            db.setDefaultDataTABLE_GROUP();
            oneTimeStart = false;
        }

        //вывод даты на экран
        timeDateFactory.setDateTime(date, time);


        //создание спиннера
        //спиннер типов@TODO сделать таблицу в базе для этих данных
        ArrayList<String> mBox = new ArrayList<String>() {
        };
        mBox.add("Мой кошелек");
        mBox.add("Карта");
        ArrayList<String> moneyType = new ArrayList<String>() {
        };
        moneyType.add("руб.");
        moneyType.add("дол.");
        moneyType.add("евро");

        SpinnerSimpleAdapter categoryAdapter = new SpinnerSimpleAdapter(view.getContext(), db.getSpDataValue());
        SpinnerSimpleAdapter moneyBoxAdapter = new SpinnerSimpleAdapter(view.getContext(), mBox);
        SpinnerSimpleAdapter moneyTypeAdapter = new SpinnerSimpleAdapter(view.getContext(), moneyType);
        spCategory = (Spinner) view.findViewById(R.id.spCategory);
        spMoneyBox = (Spinner) view.findViewById(R.id.spMoneyBox);
        spMoneyType = (Spinner) view.findViewById(R.id.spMoneyType);

        spCategory.setAdapter(categoryAdapter.getSpinnerAdapter());
        spMoneyBox.setAdapter(moneyBoxAdapter.getSpinnerAdapter());
        spMoneyType.setAdapter(moneyTypeAdapter.getSpinnerAdapter());
        return view;
    }

    public void onClick(View v) {
        db = new DataBase(getContext());
        BudgetDataContainer container = new BudgetDataContainer();
        container.setDbName(DataBaseContract.TABLE_BUDGET.TABLE_NAME);
        switch (v.getId()) {
            case R.id.btnSaveOUT:
                if (vCostsAdd.getText().toString().length() != 0) {
                    container.setDbComment(vCommentOUT.getText().toString());
                    container.setDbCosts(vCostsAdd.getText().toString());
                    container.setDbDate(tvDate.getText().toString());
                    container.setDbCategory(spCategory.getSelectedItem().toString());
                    container.setDbMoneyBox(spMoneyBox.getSelectedItem().toString());
                    container.setDbBudgetType(getClass().getSimpleName());
                    container.setDbMoneyType(spMoneyType.getSelectedItem().toString());

                    Log.d("DataBaseLog", "--------" + container.getDbCosts() + "---------");
                    Log.d(TAG, "Date: " + container.getDbDate());
                    Log.d(TAG, "--------" + String.valueOf(getClass().getSimpleName()) + "---------");
                    //Вызов метода записи значения в бд

                    db.setDataBudget(container);
                    //@TODO добавить в Strings
                    Toast.makeText(getContext(), "Трата добавлена " + container.getDbCosts(), Toast.LENGTH_SHORT).show();
                    //очистка поля ввода трат
                    vCostsAdd.setText("");

                    //вывод значений базы в лог
                    db.getDBInfo(container.getDbName());
                } else {
                    //Если не введена сумма траты@TODO добавить в Strings
                    Toast.makeText(getContext(), "Введите сумму траты.", Toast.LENGTH_LONG).show();
                    break;
                }
                break;
            case R.id.btnGetSalary:

                container.setDbComment(vCommentOUT.getText().toString());

                Log.d("DataBaseLog", "id = " + container.getDbComment());

                vGetSalary.setText(db.getData(container.getDbName(), container.getDbComment()));
                //вывод значений базы в лог
                db.getDBInfo(container.getDbName());
                break;
            case R.id.tvDate:
                Intent intent = new Intent(getContext(), ChoseDate.class);
                startActivity(intent);
                break;
            case R.id.btnMinusDate:
                timeDateFactory.minusDate(date);
                break;
            case R.id.btnPlusDate:
                timeDateFactory.plusDate(date);
                break;
        }

    }

    //обновление даты и времени
    @Override
    public void update(String date, String time) {
        this.date = date;
        tvDate.setText(date);
        tvTime.setText(time);
    }
}
