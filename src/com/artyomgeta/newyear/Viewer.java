package com.artyomgeta.newyear;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;


@SuppressWarnings("unused")
public class Viewer extends JFrame {
    private JPanel panel1;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel editorPanel;
    private JButton playButton;
    private JButton button3;
    private JComboBox comboBox1;
    private JTree tree1;
    private JProgressBar progressBar1;
    private JTabbedPane tabbedPane1;
    private JSlider slider1;
    private JButton setBackgroundButton;
    private JComboBox comboBox2;
    private JButton a1Button;
    private JButton a2Button;
    private JButton следующийВопросButton;
    private JTextField textField2;
    private JButton показатьButton;
    private JTextArea textArea1;
    private JButton fullBackgroundButton;
    private JTextField textField1;
    private JLabel team1PointsLabel;
    private JLabel team2PointsLabel;
    private JLabel team1NameLabel;
    private JLabel team2NameLabel;
    private JLabel team1LogoLabel;
    private JLabel team2LogoLabel;
    private JLabel backgroundLabel;
    private JPanel backgroundPanel;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("Файл");
    private JMenu teamMenu = new JMenu("Комманды");
    private JMenu actionMenu = new JMenu("Действие");
    private JMenu team1Menu = new JMenu("Комманда 1");
    private JMenu team2Menu = new JMenu("Комманда 2");
    private JMenuItem changeURLTeam1Item = new JMenuItem("Изменить ссылки");
    private JMenuItem changeURLTeam2Item = new JMenuItem("Изменить ссылки");
    private JMenuItem watchInfoTeam2Item = new JMenuItem("Посмотреть информацию");
    private JMenuItem watchInfoTeam1Item = new JMenuItem("Посмотреть информацию");
    private JMenuItem addToTeam1Item = new JMenuItem("Добавить балл");
    private JMenuItem addToTeam2Item = new JMenuItem("Добавить балл");
    private JMenuItem removeFromTeam2Item = new JMenuItem("Удалить балл");
    private JMenuItem removeFromTeam1Item = new JMenuItem("Удалить балл");
    private JMenu addSomethingMenu = new JMenu("Добавить ресурс...");
    private JMenuItem addBackgroundMenuItem = new JMenuItem("Добавить фон");

    private short team1_points = 0;
    private short team2_points = 0;

    /*try {
            BufferedImage img = ImageIO.read(new URL("https://artyomgeta.github.io/assets/img/me5.jpg"));
            ImageIcon icon = new ImageIcon(img);
            playButton.setIcon(Main.resizeIcon(icon, 20, 20));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    public Viewer() {

    }

    public void run() {
        setTitle("Соревнование");
        setBounds(new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(panel1);
        setUI();
    }

    private void setUI() {
        leftPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5, 20));

        this.setJMenuBar(menuBar);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.teamMenu);
        this.menuBar.add(this.actionMenu);

        this.teamMenu.add(this.team1Menu);
        this.teamMenu.add(this.team2Menu);

        this.team1Menu.add(this.addToTeam1Item);
        this.team1Menu.add(this.removeFromTeam1Item);
        this.team1Menu.add(this.watchInfoTeam1Item);
        this.team1Menu.add(this.changeURLTeam1Item);

        this.team2Menu.add(this.addToTeam2Item);
        this.team2Menu.add(this.removeFromTeam2Item);
        this.team2Menu.add(this.watchInfoTeam2Item);
        this.team2Menu.add(this.changeURLTeam2Item);

        this.a1Button.addActionListener(e -> addPoint(1));
        this.a2Button.addActionListener(e -> addPoint(2));
        this.removeFromTeam1Item.addActionListener(e -> removePoint(1));
        this.addToTeam1Item.addActionListener(e -> addPoint(1));
        this.addToTeam2Item.addActionListener(e -> addPoint(2));
        this.removeFromTeam2Item.addActionListener(e -> removePoint(2));

        this.actionMenu.add(this.addSomethingMenu);
        this.addSomethingMenu.add(this.addBackgroundMenuItem);

        this.addBackgroundMenuItem.addActionListener(e -> {
            BackgroundAdd backgroundAdd = new BackgroundAdd();
        });

        this.setBackgroundButton.addActionListener(e -> {
            try {
                backgroundLabel.setIcon(returnBackground(new URL("http://komotoz.ru/gifki/images/gifki_novyj_god/elka.gif")));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        });

        this.fullBackgroundButton.addActionListener(e -> {
            try {
                Background background = new Background(returnBackground(new URL("https://artyomgeta.github.io/assets/img/me5.jpg")));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void addPoint(int team) {
        if (team == 1) {
            team1_points++;
            team1PointsLabel.setText(String.valueOf(team1_points));
        } else {
            team2_points++;
            team2PointsLabel.setText(String.valueOf(team2_points));
        }
    }

    public ImageIcon returnBackground(URL url) {
        //URL url = new URL("http://komotoz.ru/gifki/images/gifki_novyj_god/elka.gif");
        backgroundLabel.setText(null);
        ImageIcon imageIcon = new ImageIcon(url);
        Image image = imageIcon.getImage();

        float kWidth = (float) backgroundPanel.getWidth() / (float) imageIcon.getIconWidth();
        float kHeight = (float) backgroundPanel.getHeight() / (float) imageIcon.getIconHeight();
        float k = Math.min(kWidth, kHeight);
        Image newImage = image.getScaledInstance(
                (int) ((float) imageIcon.getIconWidth() * k),
                (int) ((float) imageIcon.getIconHeight() * k),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        imageIcon = new ImageIcon(image);
System.out.println(imageIcon.getIconWidth());
System.out.println(imageIcon.getIconHeight());
System.out.println(k);
System.out.println((int) ((float) imageIcon.getIconWidth() * k));
System.out.println((int) ((float) imageIcon.getIconHeight() * k));
        return imageIcon;
    }

    private void addPoint(short team, int value) {
        if (team == 1) {
            team1_points += value;
            team1PointsLabel.setText(String.valueOf(team1_points));
        } else {
            team2_points += value;
            team2PointsLabel.setText(String.valueOf(team2_points));
        }
    }
    private void removePoint(int team) {
        if (team == 1) {
            team1_points--;
            team1PointsLabel.setText(String.valueOf(team1_points));
        } else {
            team2_points--;
            team2PointsLabel.setText(String.valueOf(team2_points));
        }
    }

    private void removePoint(short team, int value) {
        if (team == 1) {
            team1_points -= value;
            team1PointsLabel.setText(String.valueOf(team1_points));
        } else {
            team2_points -= value;
            team2PointsLabel.setText(String.valueOf(team2_points));
        }
    }
}
