package com.company;
import jdk.jfr.Frequency;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Model {

    private DataFrame dataFrame;

    public Model () {this.dataFrame  = new DataFrame(); }

    public DataFrame getFrame () { return this.dataFrame; }

    public void readCSV(File file) throws FileNotFoundException {
        dataFrame = new DataLoader().readCSV(file);
    }

    public void readJSON(File file) throws FileNotFoundException {
        dataFrame = new DataLoader().readJSON(file);
    }

    public String[] getColumnNames() {return this.dataFrame.getColumnNames(); }

    public String[][] getRows() throws Exception {
        return this.dataFrame.getRows();

    }

    public String[][] getPatientData() throws Exception {

        String[][] patients;
        patients = new String[this.dataFrame.getRowCount()][this.dataFrame.getColumnCount()];
        int i = 0;
        while (i < this.dataFrame.getRowCount()) {
            int j = 0;
            while (j < this.dataFrame.getColumnCount() - 1) {
                patients[i][j] = this.dataFrame.getValue(j,i);
                j++;
            }
            i++;
        }
        return patients;
    }



}
