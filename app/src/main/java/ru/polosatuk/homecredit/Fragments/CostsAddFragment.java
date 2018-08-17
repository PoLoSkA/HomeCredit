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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        //@TODO обновлять время и дату раз в минуту
        tvDate.setText(String.valueOf(Instant.now()));

        //создание спиннера
        //спиннер типов
        ArrayAdapter<String> spCategoryAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, db.getSpDataValue() );
        spCategoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spCategory = (Spinner) view.findViewById(R.id.spCategory);
        spCategory.setAdapter(spCategoryAdapter);
        spCategory.setSelection(0);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //спиннер кошельков @TODO вынести создание спиннеров в отдельный класс
        ArrayAdapter<String> spMoneyBoxAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, new String[]{"Мой кошелек", "Карта"} );
        spMoneyBoxAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spMoneyBox = (Spinner) view.findViewById(R.id.spMoneyBox);
        spMoneyBox.setAdapter(spMoneyBoxAdapter);
        spMoneyBox.setSelection(0);
        spMoneyBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

   return view;
    }

    @Override
    public void onClick(View v) {
        db = new DataBase(getContext());
        String dbName = DataBaseContract.TABLE_BUDGET.TABLE_NAME;
        switch (v.getId()){
            case R.id.btnSaveOUT:
                if (vCostsAdd.getText().toString().length() != 0){

                    dbComment = vCommentOUT.getText().toString();
                    String dbCosts = vCostsAdd.getText().toString();
                    String dbDate = tvDate.getText().toString();
                    String dbCategory = spCategory.getSelectedItem().toString();
                    String dbMoneyBox = spMoneyBox.getSelectedItem().toString();

                    Log.d("DataBaseLog", "--------" + dbCosts + "---------");
                    Log.d(TAG, "Date: " + dbDate);
                    //Вызов метода записи значения в бд

                    db.setDataBudget(dbName, dbCosts, dbComment, dbDate, dbCategory, dbMoneyBox);
                    Toast.makeText(getContext(), "Трата добавлена " + dbCosts,Toast.LENGTH_SHORT).show();
                    //очистка поля ввода трат
                    vCostsAdd.setText("");

                    //вывод значений базы в лог
                    db.getDBInfo(dbName);
                }else{
                    //Если не введена сумма траты
                    Toast.makeText(getContext(), "Введите сумму траты.", Toast.LENGTH_LONG).show();
                    break;
                }
                break;
            case R.id.btnGetSalary:

                dbComment = vCommentOUT.getText().toString();

                Log.d("DataBaseLog", "id = " + dbComment);

                vGetSalary.setText(db.getData(dbName, dbComment));
                //вывод значений базы в лог
                db.getDBInfo(dbName);
                break;
            case R.id.btnNextPage:
                Intent intent = new Intent(getContext(), ChoseDate.class);
                startActivity(intent);
                break;
        }

    }
}
