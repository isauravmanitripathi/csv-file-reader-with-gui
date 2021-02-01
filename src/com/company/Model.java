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

    private String convertDateToCorrectFormat(String date) {
        String[] splitDate = date.split("-");
        Collection.reverse(Arrays.asList(splitDate));
        return splitDate[0] + "/" + splitDate[1] + "/" + splitDate[2];
    }

    private boolean isDead(int index) throws Exception {return dataFrame.getColumns().get(2).getRowValue(index).equals("");}

    public String oldestPerson() throws Exception {
        ArrayList<Date> dates;
        dates = new ArrayList<>();
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("dd/mm/yyyy");
        ArrayList<Column> columns = dateFrame.getColumns();
        for (int j = 0; j < columns.size(); j++) {
            Column column = columns.get(j);
            if (!column.getName().toLowerCase().equals("birthdate")) {
                continue;
            }
            int i = 0;
            while (i < column.getSize()) {
                String date = isDead(i) ? column.getRowValue(i) : "2020-03-25"; // ignore dead people
                dates.add(formatter.parse(convertDateToCorrectFormat(date)));
                i++;
            }
            int index = dates.indexOf(Collections.min(dates));
            String firstName = dataFrame.getColumns().get(7).getRowValue(index); // get first name of oldest
            String lastName = dataFrame.getColumns().get(8).getRowValue(index); // get last name of oldest
            return firstName + " " + lastName;
        }
        return null;

    }

    private String countFrequency(String columnName) throws Exception {
        StringBuilder data = new StringBuilder();
        ArrayList<String> races = new ArrayList<>();
        ArrayList<Column> columns = dataFrame.getColumns();
        for (int j = 0, columnsSize = columns.size(); j < columnsSize; j++) {
            Column column = columns.get(j);
            if (!column.getName().toLowerCase().equals(columnName)) {
                continue;
            }
            for (int i = column.getSize() - 1; i >= 0; i--) races.add(column.getRowValue(i));
            Set<String> set = new HashSet<String>(races);
            set.forEach(value -> data.append(value).append(": ").append(Collections.frequency(races, value)).append(System.lineSeparator()));
            data.setLength(data.length() - 1);
            return data.toString();
        }
        return null;
    }


    public String raceData() throws Exception {
        return countFrequency("race");
    }


    public String ethnicityData() throws Exception {
        return countFrequency("ethnicity");
    }


    public String cityData() throws Exception {
        return countFrequency("city");
    }


    public String genderData() throws Exception {
        return countFrequency("gender");
    }


    public String birthplaceData() throws Exception {
        return countFrequency("birthplace");
    }



}
