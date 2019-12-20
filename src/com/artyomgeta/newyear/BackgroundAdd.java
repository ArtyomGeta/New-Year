package com.artyomgeta.newyear;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public class BackgroundAdd extends JDialog {

    private JPanel panel1;
    private JTextField nameField;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private JTextField urlField;
    private JTextField pathField;
    private JButton chooseFileButton;
    private JButton applyButton;
    private JButton cancelButton;
    private JCheckBox customSizeCheckBox;
    private JTextField size1Field;
    private JTextField size2Field;

    public BackgroundAdd() {
        setContentPane(panel1);
        setUI();
    }

    private void setUI() {
        setTitle("Выберите файл");
        setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 320, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 240, 500, 200));
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
        setContentPane(panel1);
        this.pack();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        radio1.addActionListener(e -> {
            chooseFileButton.setEnabled(false);
            pathField.setEnabled(false);
            urlField.setEnabled(true);
            radio2.setSelected(false);
        });

        radio2.addActionListener(e -> {
            urlField.setEnabled(false);
            chooseFileButton.setEnabled(true);
            pathField.setEnabled(true);
            radio1.setSelected(false);
        });

        customSizeCheckBox.addActionListener(e -> {
            if (!customSizeCheckBox.isSelected()) {
                size1Field.setEditable(true);
                size2Field.setEditable(true);
            } else {
                size1Field.setEditable(false);
                size2Field.setEditable(false);
            }
        });


        cancelButton.addActionListener(e -> dispose());

        applyButton.addActionListener(e -> {
            if (new File("Backgrounds.json").exists()) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("Backgrounds.json"));
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while (line != null) {
                        sb.append(line);
                        line = br.readLine();
                    }
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    FileWriter fileWriter = new FileWriter(new File("Backgrounds.json"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
