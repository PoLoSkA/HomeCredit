package ru.polosatuk.homecredit.dataTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ru.polosatuk.homecredit.BudgetDataContainer;

public class DataBase {
    private static final String TAG = "DataBaseLog";
    private DBHelper dbh;
    private SQLiteDatabase db;


    // Подключаемся к БД
    public DataBase(Context context) {
        dbh = new DBHelper(context);
    }



//вывод в лог значений БД
   public void logCursor(Cursor c) {
    if (c != null) {
        if (c.moveToFirst()) {
            String str;
                do {
                    str = "";
                     for (String cn : c.getColumnNames()) {
                         str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                      }
          Log.d(TAG, str);
         } while (c.moveToNext());
          }
     } else
        Log.d(TAG, "Cursor is null");
    }
    //для получения имени БД
    public void getDBInfo(String table){
        db = dbh.getReadableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        logCursor(cursor);
        cursor.close();
        dbh.close();
    }
    //Добавление данных в БД
    public void setDataBudget(BudgetDataContainer container){

        Log.d(TAG, "Insert in table " + container.getDbName() +
                " /Comment = " + container.getDbComment() +
                " /Date = " + container.getDbDate() +
                " /Salary = " + container.getDbCosts() +
                "/Category = "+ container.getDbCategory() +
                "/moneyBox = " + container.getDbMoneyBox() +
                "/budgetType = " + container.getDbBudgetType() +
                "/dbMoneyType = " + container.getDbMoneyType());


        db = dbh.getWritableDatabase();
        db.beginTransaction();
        ContentValues cv = new ContentValues();

        cv.put(DataBaseContract.TABLE_BUDGET.COLUMN_COMMENT, container.getDbComment());
        cv.put(DataBaseContract.TABLE_BUDGET.COLUMN_SALARY, container.getDbCosts());
        cv.put(DataBaseContract.TABLE_BUDGET.COLUMN_DATE, container.getDbDate());
        cv.put(DataBaseContract.TABLE_BUDGET.COLUMN_CATEGORY, container.getDbCategory());
        cv.put(DataBaseContract.TABLE_BUDGET.COLUMN_MONEYBOX, container.getDbMoneyBox());
        cv.put(DataBaseContract.TABLE_BUDGET.COLUMN_TYPE, container.getDbBudgetType());
        cv.put(DataBaseContract.TABLE_BUDGET.COLUMN_MONEYTYPE, container.getDbMoneyType());

        db.insert(container.getDbName(),null, cv);
        db.setTransactionSuccessful();
        db.endTransaction();
        dbh.close();

    }
    //Просмотр данных в БД
    public String getData(String table, String id){
        db = dbh.getReadableDatabase();
        String salary = "";
        String selection = " " + DataBaseContract.TABLE_BUDGET.COLUMN_ID + " = ? ";
        String[] selectionArgs = new String[] {String.valueOf(id)};
        Cursor cursor = db.query(table, null, selection, selectionArgs,null,null,null);
        if( cursor != null && cursor.moveToFirst() ){
            salary = String.valueOf(cursor.getLong(cursor.getColumnIndex(DataBaseContract.TABLE_BUDGET.COLUMN_SALARY))) + " " +
            cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_BUDGET.COLUMN_MONEYBOX)) + " " +
            cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_BUDGET.COLUMN_CATEGORY));
        Log.d(TAG, "SHOW: " + salary);
        }
        cursor.close();
        dbh.close();
        return salary;
    }
    public void setDefaultDataTABLE_GROUP(){
        //Дефолтное наполнение спинера категорий
        db = dbh.getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(DataBaseContract.TABLE_GROUP.TABLE_NAME,null,null,null,null,null,null);
      // Если в базе есть строки, ничего не делаем
       if (cursor.moveToFirst()){
            Log.d(TAG, "Table Group was created early " + cursor.moveToFirst());
        }else {
           //Если в базе нет строк, то наполняем ее значениями по умолчанию
            Log.d(TAG, "Start create data base: " + DataBaseContract.TABLE_GROUP.TABLE_NAME);
            //@TODO добавить в Strings
            String[] group = new String[]{"Без категории","Еда", "  Продукты", "    Обеды и перекусы", "    Кофе", "Транспорт", "   Проезд","   Бензин", "  Траты на машину"};
            String[] catID =new String[]{ "0","1", "1", "1", "1", "2", "2", "2", "2"};
            String[] gravity = new String[]{"0","0","1","2","3","0","1","2","3"};
            for (int i = 0; i < group.length; i++){
                ContentValues cv = new ContentValues();
                cv.put(DataBaseContract.TABLE_GROUP.COLUMN_GROUP_NAME, group[i]);
                cv.put(DataBaseContract.TABLE_GROUP.COLUMN_CATEGORY_ID, catID[i]);
                cv.put(DataBaseContract.TABLE_GROUP.COLUMN_GRAVITY, gravity[i]);
                db.insert(DataBaseContract.TABLE_GROUP.TABLE_NAME, null, cv);

                Log.d(TAG, "Insert in table " + DataBaseContract.TABLE_GROUP.TABLE_NAME +
                        " Group = " + cv.get(DataBaseContract.TABLE_GROUP.COLUMN_GROUP_NAME) +
                        " CatID = " + cv.get(DataBaseContract.TABLE_GROUP.COLUMN_CATEGORY_ID) +
                        " Gravity = " + cv.get(DataBaseContract.TABLE_GROUP.COLUMN_GRAVITY));
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        dbh.close();
    }
    public ArrayList<String> getSpDataValue(){
        ArrayList<String> spGroupData = new ArrayList<>();
        int i = 0;
        db = dbh.getWritableDatabase();
        String[] columns = new String[]{DataBaseContract.TABLE_GROUP.COLUMN_GROUP_NAME};
        String orderBy =  DataBaseContract.TABLE_GROUP.COLUMN_CATEGORY_ID;
        Log.d(TAG, "OrderBY = "  + orderBy);
        Cursor cursor = db.query(DataBaseContract.TABLE_GROUP.TABLE_NAME,columns,null,null,null,null,orderBy );
        if (cursor != null && cursor.moveToFirst())
        {
            int id = cursor.getColumnIndex(DataBaseContract.TABLE_GROUP.COLUMN_GROUP_NAME);
            do{
                spGroupData.add(cursor.getString(id));
                i++;
            }while (cursor.moveToNext());

            //запись в лог @TODO удалить после отладки Начало
            cursor.moveToFirst();
            do {
                Log.d(TAG, "SPINNER Group = " + cursor.getString(id));
            }while (cursor.moveToNext());
            //конец удаляемого
        }
        cursor.close();
        dbh.close();
        return spGroupData;
    }

    // класс для работы с БД
    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DataBaseContract.DB_NAME, null, DataBaseContract.DB_VERSION);
        }

        // создаем таблицу
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "--- onCreate database ---" + DataBaseContract.TABLE_BUDGET.TABLE_NAME);
            db.execSQL(DataBaseContract.TABLE_BUDGET.CREATE_TABLE);
           Log.d(TAG, "--- onCreate database ---" + DataBaseContract.TABLE_GROUP.TABLE_NAME);
            db.execSQL(DataBaseContract.TABLE_GROUP.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
