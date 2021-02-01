package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class GUI implements ActionListener{

    private int ON_CLOSE;
    private Model model;
    private JFileChooser fileChooser;
    private FileNameExtensionFilter filter;
    private JSplitPane splitPane;
    private JTextField textField;
    private TableRowSorter<TabelModel> rowSorter;
    private JTable table;
    private JFrame jFrame;
    private JMenu menuFileButton;
    private JMenuItem optionMenuOpen;
    private JLabel welcome;
    private JPanel boxPanel;
    private JCheckBox[] boxes;
    private Dimension screenSize;
    private boolean csv;

    public GUI() {
        this.model = ModelFactory.getModel();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.jFrame = new JFrame("CSV File Reader");
        this.fileChooser = new JFileChooser();
        this.filter = new FileNameExtensionFilter("csv","csv" ,"file");
        this.ON_CLOSE = JFrame.EXIT_ON_CLOSE;

    }

    public GUI(int ON_CLOSE) {
        this.model = new Model();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();;
        this.jFrame = new JFrame ("CSV File Reader");
        this.fileChooser = new JFileChooser();
        this.filter = new FileNameExtensionFilter("csv","csv");
        this.ON_CLOSE = ON_CLOSE;

    }

    @Override
    public void actionPerformed(ActionEvent event) {

        if (!(event.getSource() instanceof JCheckBox)) {
            String name = ((Component) event.getSource()).getName();
            if ("Open".equals(name)) {
                fileChooser.addChoosableFileFilter(filter); // this will only allow CSV files
                fileChooser.setAcceptAllFileFilterUsed(false);
                if (fileChooser.showOpenDialog(splitPane) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (file.getName().toLowerCase().endsWith(".csv")) {
                        csv = true;

                        try {
                            model.readCSV(file);
                            updateFrame(model.getFrame().file.getName());
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(new JFrame("ERROR Message"), "Program was unable to read the choosen file", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if (file.getName().toLowerCase().endsWith(".json")) {
                        csv = true;

                        try {
                            model.readCSV(file);
                            updateFrame(model.getFrame().file.getName());
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(new JFrame("ERROR Message"), "Program was unable to read the choosen file", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            } else if ("Save as JSON".equals(name)) { // this is the part where the user clicks save as json button
                fileChooser.resetChoosableFileFilters();
                filter = new FileNameExtensionFilter("json", "json");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setDialogTitle("Which directory to save");
                int userSelection = fileChooser.showSaveDialog(splitPane); // open a file dialog to user can naviagte to target directory
                switch (userSelection) {
                    case JFileChooser.APPROVE_OPTION: {
                        String path = fileChooser.getSelectedFile().getPath();
                        if (path.endsWith(".json")) {
                        } else {
                            path += ".json";
                        }
                        try {
                            new JSONWriter(model.getFrame()).writeJSON(path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (! "New Window..".equals(name)) {
                    if ("Exit".equals(name)) {
                        System.exit(0); // end program here
                    }
                } else {
                    new GUI(JFrame.DISPOSE_ON_CLOSE).run(); // this will open a new GUI
                }
        }
    } else {
            for (int i = 0; i < boxes.length; i++) {
                if (boxes[i].isSelected()) show(i);
                else if (!boxes[i].isSelected()) hide(i);

            }
        }




}
