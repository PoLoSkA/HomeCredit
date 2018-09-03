package ru.polosatuk.homecredit.dataTable;

import android.provider.BaseColumns;


public final class DataBaseContract {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "creditDb";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String COMMA_SEP = ",";

    private DataBaseContract(){}

        //Таблица Расходов
    public static abstract class TABLE_BUDGET implements BaseColumns{
        public static final String TABLE_NAME = "budget"; //имя таблицы
        public static final String COLUMN_ID = "id"; // id - integer
        public static final String COLUMN_SALARY = "salary"; // потраченая сумма - integer
        public static final String COLUMN_COMMENT = "comment";   //комментарий - текстовое
        public static final String COLUMN_DATE = "date"; //дата траты - текстовое
        public static final String COLUMN_CATEGORY ="category"; //тип траты, выбирается из таблицы типов
        public static final String COLUMN_MONEYBOX = "moneyBox"; //кошелек - текст
        public static final String COLUMN_TYPE = "budgettype";// 0 - расход / 1 - доход
        public static final String COLUMN_MONEYTYPE = "moneytype";//тип валюты

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_SALARY + INTEGER_TYPE + COMMA_SEP +
                COLUMN_COMMENT + TEXT_TYPE + COMMA_SEP +
                COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_CATEGORY + TEXT_TYPE  + COMMA_SEP +
                COLUMN_MONEYBOX + TEXT_TYPE + COMMA_SEP +
                COLUMN_TYPE + INTEGER_TYPE + COMMA_SEP +
                COLUMN_MONEYTYPE + TEXT_TYPE + " )";
            public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static abstract class TABLE_GROUP implements BaseColumns{
        public static final String TABLE_NAME = "tableGroup";
        public static final String COLUMN_ID = "idGroup";
        public static final String COLUMN_GROUP_NAME = "groupName"; //Названия категорий.  табуляцией выделяем подкатегории
        public static final String COLUMN_CATEGORY_ID = "categoryID";   //равен для каждой категории
        public static final String COLUMN_GRAVITY = "categoryGravity";  // определяет положение категорий

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_GROUP_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_CATEGORY_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_GRAVITY + INTEGER_TYPE +  " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    //@TODO добавлять таблицу под каждый спиннер или нет, вот в чем вопрос :(

}
