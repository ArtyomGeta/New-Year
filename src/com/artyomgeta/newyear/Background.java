package com.artyomgeta.newyear;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Background extends JDialog {
    private JPanel panel1;
    private JLabel imageLabel;
    private JPanel panel2;

    public Background(Icon icon) {
        this.setBounds(new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        this.setBackground(Color.BLACK);
        this.panel1.setBackground(Color.BLACK);
        this.panel2.setBackground(Color.BLACK);
        this.imageLabel.setBackground(Color.BLACK);
        this.imageLabel.setForeground(Color.BLACK);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setUndecorated(true);
        this.imageLabel.setIcon(icon);
        this.setVisible(true);
        this.setContentPane(panel1);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) dispose();
            }
        });
    }

}
