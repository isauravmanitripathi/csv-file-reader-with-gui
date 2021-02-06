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


    pulic String[] getColumnNames() {
        String[] columnNames;
        columnNames = new String[this.getColumnCount()];
        int i = 0;
        while (i < this.getColumnCount()) {
            columnNames[i] = this.getColumns().get(i).getName();
            i++;
        }
        return columnNames;
    }

    public String[][] getRows() throws Exception {
        int numColumns = this.getColumnCount();
        int numRows = this.getRowCount();
        String [][] data = new String[numRows][numColumns];
        for (int i = 0; i < numColumns; i++) {
            int j = 0;
            while (j < numRows) {
                data[j][i] = this.getValue(i,j);
                j++;
            }
        }

        return data;
    }

    public int getRowCount() { return columns.get(0).getSize(); }

}
