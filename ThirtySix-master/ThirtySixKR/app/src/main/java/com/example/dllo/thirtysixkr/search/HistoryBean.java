package com.example.dllo.thirtysixkr.search;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

public class HistoryBean {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    String historyQuery;

    public int getId() {
        return id;
    }

    public String getHistoryQuery() {
        return historyQuery;
    }

    public void setHistoryQuery(String historyQuery) {
        this.historyQuery = historyQuery;
    }
}
