package com.lescure.toulousesacchiens.model;

import java.util.ArrayList;

public class ResultBean {

    private ArrayList<RecordBean> records;

    public ArrayList<RecordBean> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<RecordBean> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "ResultBean [records = " + records + "]";
    }
}
