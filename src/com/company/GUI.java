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




}
