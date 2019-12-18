package com.artyomgeta.newyear;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static void main(String[] args) {
        new Viewer().run();
    }
}
