package com.artyomgeta.newyear;

import javax.swing.*;
import java.awt.*;

public class Add extends JDialog {

    public Add() {
        this.setTitle("Добавить файл");
        this.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 320, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 240, 640, 480));
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

}
