package com.company;

import java.util.*;

public class DataFrame {

    private ArrayList<Column> columns;

    public DataFrame() { this.columns = new ArrayList<Column>() ;}

    public ArrayList<Column> getColumns() { return column; }

    public void addColumn(String columnName) { this.columns.add(new Column(columnName));}

    public boolean columnExists(String coulmnName) {
        boolean result = false;
        ArrayList<Column> columnArrayList = this.columns;
        for (int i = 0; i < columnArrayList.size(); i++ ) {
            Column column = columnArrayList.get(i);
            if (!column.getName().equals(columnName)) {
                continue;
            }
            result = true;
            break;
        }
        return result;
    }




}
