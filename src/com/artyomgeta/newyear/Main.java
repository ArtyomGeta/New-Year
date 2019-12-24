package com.artyomgeta.newyear;

import javax.swing.*;
import java.awt.*;

public abstract class Main {

    public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        try {
            new Viewer().run();
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Не удалось загрузить один из файлов. Попробуйте ещё раз.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
