package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TableView;
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
                if (!"New Window..".equals(name)) {
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

    // Diplaying the date and updating the GUI

    private void updateFrame(DataFrame dataFrame, String newName) throws Exception {
        jFrame.setTitle(newName); // this will the change the window title
        jFrame.remove(welcome);
        menuFileButton.remove(optionMenuOpen); // removing no longer needed components
        JMenuItem newWindow = new JMenuItem("New Window...");
        newWindow.setName("New Window...");
        newWindow.addActionListener(this);
        newWindow.setFont(new Font("ComicSansMS", Font.PLAIN, newWindow.getFont().getSize()));
        menuFileButton.add(newWindow);
        if (!csv) {
        } else {
            JMenuItem saveAsJSON;
            saveAsJSON = new JMenuItem("Save as JSON");
            saveAsJSON.setName("Save as JSON");
            saveAsJSON.addActionListener(this);
            saveAsJSON.setFont(new Font("ComicSansMS", Font.PLAIN, saveAsJSON.getFont().getSize()));
            menuFileButton.add(saveAsJSON);
        }

        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("Exit");
        exit.addActionListener(this);
        exit.setFont(new Font ("ComicSansMS", Font.PLAIN, exit.getFont().getSize()));
        menuFileButton.add(exit);
        createCheckBoxes(model.getColumnNames());
        table = new JTable(model.getRows(), model.getColumnNames()) {
            public boolean isCellEditable (int row, int column) { return false; }
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                Color color = table.getBackground();
                if (isRowSelected(row)) {
                    return c;
                }
                c.setBackground(row % 2 == 0 ? getBackground() : new Color(255, 240, 240));
                return c;
            }
        };

        table.setFont(new Font("ComicSansMS", Font.PLAIN, table.getFont().getSize()));
        table.getTableHeader().setReorderingAllowed(false);
        table.setPreferredScrollableViewportSize(new Dimension(500,100));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane;
        scrollPane = new JScrollPane(table);
        jFrame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        jFrame.setSize((int) (screenSize.width * 4.0/5.0), (int) (screenSize.height * 4.0/5.0));
        jFrame.setLocationRelativeTo(null);
        jFrame.add(scrollPane);
        JPanel bottomPanel;
        bottomPanel = new JPanel();
        textField = new JTextField(20);
        rowSorter = new TableRowSorter<TabelModel>(table.getModel());
        table.setRowSorter(rowSorter);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textField.getText();
                // ignore this case in search
                switch (text.trim().length()) {
                    case 0:
                        rowSorter.setRowFilter(null);
                        break;
                    default:
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        break;
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textField.getText();
                if (text.trim().length() != 0) {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                } else {
                    rowSorter.setRowFilter(null);
                }

            }

            @Override
            public void changedUpdate(DocumentEvent e) { thow new UnsupportedOperationException();

            }
        });

        bottomPanel.add(new JLabel("Search"), BorderLayout.WEST);
        bottomPanel.add(textField);
        jFrame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);

    }

    // this is the part where we create checkboxes to filter the data

    private void createCheckBoxes(String [] columnNames) {
        boxes = new JCheckBox[columnNames.length];
        boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        int i = 0;
        while (i < columnNames.length) {
            boxes[i] = new JCheckBox(columnNames[i]);
            boxes[i].setFont(new Font("ComicSansMS", Font.PLAIN, boxes[i].getFont().getSize()));
            boxes[i].setBorder(new EmptyBorder(0,0,0,0));
            boxes[i].setSelected(true);
            boxes[i].addActionListener(this);
            boxPanel.add(boxes[i]);
            i++;
        }
        jFrame.getContentPane().add(BorderLayout.EAST, boxPanel);
    }

    // hiding the relevant column of data

    private void hide(int index) {
        table.getColumnModel().getColumn(index).setMaxWidth(0);
        table.getColumnModel().getColumn(index).setMinWidth(0);
        table.getColumnModel().getColumn(index).setWidth(0);
        table.getColumnModel().getColumn(index).setPreferredWidth(0);
    }

    private void show(int index) {
        table.getColumnModel().getColumn(index).setMaxWidth(125);
        table.getColumnModel().getColumn(index).setMinWidth(0);
        table.getColumnModel().getColumn(index).setWidth(125);
        table.getColumnModel().getColumn(index).setPreferredWidth(125);

    }
    // when the program will be launched, this will initialize the gui

    public void run() {
        jFrame.setDefaultCloseOperation(this.ON_CLOSE);
        jFrame.setSize(screenSize.width / 2, screenSize.height/2);
        JMenuBar menuBar = new JMenuBar();
        menuFileButton = new JMenu("file");
        menuFileButton.setFont(new Font("ComicSansMS", Font.PLAIN, menuFileButton.getFont().getSize()));
        menuBar.add(menuFileButton);
        optionMenuOpen = new JMenuItem("open");
        optionMenuOpen.setFont(new Font("ComicsSansMS", Font.PLAIN, optionMenuOpen.getFont().getSize()));
        optionMenuOpen.setName("Open");
        menuFileButton.add(optionMenuOpen);
        optionMenuOpen.addActionListener(this);
        welcome = new JLabel("<html><center>Click" + // the text when GUI is launched
                "<br/><center><span style=\"color:#B93519;font-family: 'Courier';font-size: 25px\">File &#8594; Open</span>" +
                "<br/><center>to load a file</center></html>", SwingConstants.CENTER);
        welcome.setFont(new Font("ComicSansMS", Font.PLAIN, 28));
        jFrame.getContentPane().add(BorderLayout.CENTER, welcome);
        jFrame.getContentPane().add(BorderLayout.NORTH, menuBar);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

    }



}
