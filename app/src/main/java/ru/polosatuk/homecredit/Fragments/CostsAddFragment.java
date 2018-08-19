package ru.polosatuk.homecredit.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.Instant;

import ru.polosatuk.homecredit.BudgetDataContainer;
import ru.polosatuk.homecredit.ChoseDate;
import ru.polosatuk.homecredit.DataTable.DataBase;
import ru.polosatuk.homecredit.DataTable.DataBaseContract;
import ru.polosatuk.homecredit.R;


public class CostsAddFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "CostsFragment";
    TextView vGetSalary; //тестовый вывод на экран УДАЛИТЬ
    TextView tvDate; //тестовый вывод на экран УДАЛИТЬ
    EditText vCostsAdd; // ввод потраченной суммы
    EditText vCommentOUT; // комментарий к потраченной сумме
    Button btnSave,btnGet;
    DataBase db;
    String dbComment;
    Spinner spCategory, spMoneyBox;
    Boolean oneTimeStart = true;


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "----onPause----");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "----onResume----");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "----onStop----");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "----onDetach----");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "----onStart----");
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "---OnCreateView---");
        View view = inflater.inflate(R.layout.fragment_costsadd, container, false);

        vCostsAdd = (EditText) view.findViewById(R.id.vCostsAdd);
        vCommentOUT = (EditText) view.findViewById(R.id.vCommentOUT);
        vGetSalary = (TextView) view.findViewById(R.id.salaryView);
        tvDate = (TextView) view.findViewById(R.id.tvDate);

        btnGet = (Button) view.findViewById(R.id.btnGetSalary);
        btnGet.setOnClickListener(this);

        btnSave = (Button) view.findViewById(R.id.btnSaveOUT);
        btnSave.setOnClickListener(this);



        if (oneTimeStart){
            db = new DataBase(view.getContext());
            db.setDefaultDataTABLE_GROUP();
            oneTimeStart = false;
        }
        //Выводим дату на экран @TODO написать парсер даты и вывод ее в формате dd.mm.yyyy hh:mm
        //@TODO обновлять время и дату раз в минуту в отдельном потоке
        tvDate.setText(String.valueOf(Instant.now()));

        //создание спиннера
        //спиннер типов
        ArrayAdapter<String> spCategoryAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, db.getSpDataValue() );
        spCategoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spCategory = (Spinner) view.findViewById(R.id.spCategory);
        spCategory.setAdapter(spCategoryAdapter);
//        spCategory.setSelection(0);
//        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        //спиннер кошельков @TODO вынести создание спиннеров в отдельный класс
        ArrayAdapter<String> spMoneyBoxAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, new String[]{"Мой кошелек", "Карта"} );
        spMoneyBoxAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spMoneyBox = (Spinner) view.findViewById(R.id.spMoneyBox);
        spMoneyBox.setAdapter(spMoneyBoxAdapter);
//        spMoneyBox.setSelection(0);
//        spMoneyBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//
//
//  });
        return view;
    }
    public void onClick(View v) {
        db = new DataBase(getContext());
        BudgetDataContainer container = new BudgetDataContainer();
        container.setDbName(DataBaseContract.TABLE_BUDGET.TABLE_NAME);
        switch (v.getId()){
            case R.id.btnSaveOUT:
                if (vCostsAdd.getText().toString().length() != 0){
                    container.setDbComment(vCommentOUT.getText().toString());
                    container.setDbCosts(vCostsAdd.getText().toString());
                    container.setDbDate(tvDate.getText().toString());
                    container.setDbCategory(spCategory.getSelectedItem().toString());
                    container.setDbMoneyBox(spMoneyBox.getSelectedItem().toString());
                    container.setDbBudgetType(getClass().getSimpleName());
                    container.setDbMoneyType("руб"); //@TODO реализовать спиннер с выбором типов валюты

                    Log.d("DataBaseLog", "--------" + container.getDbCosts() + "---------");
                    Log.d(TAG, "Date: " + container.getDbDate());
                    Log.d(TAG, "--------" + String.valueOf(getClass().getSimpleName())+ "---------");
                    //Вызов метода записи значения в бд

                    db.setDataBudget(container);
                    //@TODO добавить в Strings
                    Toast.makeText(getContext(), "Трата добавлена " + container.getDbCosts(),Toast.LENGTH_SHORT).show();
                    //очистка поля ввода трат
                    vCostsAdd.setText("");

                    //вывод значений базы в лог
                    db.getDBInfo(container.getDbName());
                }else{
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
            case R.id.btnNextPage:
                Intent intent = new Intent(getContext(), ChoseDate.class);
                startActivity(intent);
                break;
        }

    }
}
