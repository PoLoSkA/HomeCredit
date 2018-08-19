package ru.polosatuk.homecredit;

import android.util.Log;

public class BudgetDataContainer {
    private String dbName;
    private long dbCosts;
    private String dbComment;
    private String dbDate ;
    private String dbCategory;
    private String dbMoneyBox;
    private String dbBudgetType ;
    private String dbMoneyType;


    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public long getDbCosts() {
        return dbCosts;
    }

    public void setDbCosts(String dbCosts) {
        String rowSalary = dbCosts.replaceAll("[.,]", "");
        this.dbCosts = Long.parseLong(rowSalary);
    }

    public String getDbComment() {
        return dbComment;
    }

    public void setDbComment(String dbComment) {
        this.dbComment = dbComment;
    }

    public String getDbDate() {
        return dbDate;
    }

    public void setDbDate(String dbDate) {
        this.dbDate = dbDate;
    }

    public String getDbCategory() {
        return dbCategory;
    }

    public void setDbCategory(String dbCategory) {
        this.dbCategory = dbCategory;
    }

    public String getDbMoneyBox() {
        return dbMoneyBox;
    }

    public void setDbMoneyBox(String dbMoneyBox) {
        this.dbMoneyBox = dbMoneyBox;
    }

    public int getDbBudgetType() {
        switch (dbBudgetType) {
            case "CostsAddFragment":
                return StaticAppConstant.COSTS;
            case "IncomeAddFragment":
                return StaticAppConstant.INCOME;
        }
        Log.d("CostsFragment", "---Что-то пошло не так... записано 2 getBbBudgetType()");
        return 2;
    }

    public void setDbBudgetType(String dbBudgetType) {
        this.dbBudgetType = dbBudgetType;

    }

    public String getDbMoneyType() {
        return dbMoneyType;
    }

    public void setDbMoneyType(String dbMoneyType) {
        this.dbMoneyType = dbMoneyType;
    }
}
