package com.artyomgeta.newyear;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


@SuppressWarnings("unused")
public class Viewer extends JFrame {
    private JPanel panel1;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel editorPanel;
    private JButton playButton;
    final AudioInputStream[] audioIn = {null};
    final Clip[] clip = {null};
    private JTree tree1;
    private JProgressBar progressBar1;
    private JTabbedPane tabbedPane1;
    AtomicLong timeMusic = new AtomicLong();
    private JButton setBackgroundButton;
    private JComboBox comboBox2;
    private JButton a1Button;
    private JButton a2Button;
    private JButton следующийВопросButton;
    private JTextField textField2;
    private JButton показатьButton;
    private JTextArea textArea1;
    private JButton fullBackgroundButton;
    private JLabel team1PointsLabel;
    private JLabel team2PointsLabel;
    private JLabel team1NameLabel;
    private JLabel team2NameLabel;
    private JLabel team1LogoLabel;
    private JLabel team2LogoLabel;
    private JLabel backgroundLabel;
    private JPanel backgroundPanel;
    private JButton stopButton;
    private JComboBox musicComboBox;
    private JSlider musicSlider;
    private JLabel timeLabel;
    private JTextField textField1;
    private JToolBar mainToolBar;
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
    private JButton закрытьButton;
    private JToolBar closeToolBar;
    private JToolBar bottomToolBar;
    private JMenu timeMenu = new JMenu("Время...");
    private JMenuItem continueMenuItem = new JMenuItem("Продолжить");
    private JMenuItem pauseMenuItem = new JMenuItem("Пауза");
    private JMenu viewMenu = new JMenu("Вид...");
    private JMenuItem setToolBarInvisibleItem = new JMenuItem("Скрыть меню");
    private JMenuItem setFullScreenItem = new JMenuItem("Растянуть на весь экран");
    private JMenuItem setBottomToolBarInvisible = new JMenuItem("Скрыть меню статуса");

    private short team1_points = 0;
    private short team2_points = 0;
    private JMenuItem exitItem = new JMenuItem("Выйти");
    private int workingMinutes = 0;
    private int workingSeconds = 0;


    public Viewer() {
        setUI();
    }

    public void run() {
        setTitle("Соревнование");
        setBounds(new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(panel1);
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

        this.actionMenu.add(this.timeMenu);
        this.timeMenu.add(this.continueMenuItem);
        this.timeMenu.add(this.pauseMenuItem);

        this.fileMenu.add(this.viewMenu);
        this.viewMenu.add(this.setToolBarInvisibleItem);
        this.viewMenu.add(this.setBottomToolBarInvisible);
        this.viewMenu.add(this.setFullScreenItem);
        this.fileMenu.add(this.exitItem);

        this.setToolBarInvisibleItem.addActionListener(e -> {
            if (this.mainToolBar.isVisible()) {
                this.mainToolBar.setVisible(false);
                this.setToolBarInvisibleItem.setText("Показать меню");
            } else {
                this.mainToolBar.setVisible(true);
                this.setToolBarInvisibleItem.setText("Скрыть меню");
            }
        });

        this.exitItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите выйти?", "Подтвердите", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_NO_OPTION) {
                System.exit(1);
            }
        });

        this.setBottomToolBarInvisible.addActionListener(e -> {
            if (this.bottomToolBar.isVisible()) {
                this.bottomToolBar.setVisible(false);
                this.setBottomToolBarInvisible.setText("Показать меню статуса");
            } else {
                this.bottomToolBar.setVisible(true);
                this.setBottomToolBarInvisible.setText("Скрыть меню статуса");
            }
        });

        this.setFullScreenItem.addActionListener(e -> {
            if (!this.isUndecorated()) {
                this.dispose();
                this.setUndecorated(true);
                this.setVisible(true);
                this.closeToolBar.setVisible(true);
                this.setFullScreenItem.setText("Свернуть");
            } else {
                this.dispose();
                this.setUndecorated(false);
                this.setVisible(true);
                this.setFullScreenItem.setText("Растянуть на весь экран");
            }
        });

        this.addBackgroundMenuItem.addActionListener(e -> {
            BackgroundAdd backgroundAdd = new BackgroundAdd();
        });

        AtomicBoolean gameIsRunning = new AtomicBoolean(false);

        int millisInAMinute = 60000;
        long time = System.currentTimeMillis();

        this.continueMenuItem.addActionListener(e -> {

        });

        this.setBackgroundButton.addActionListener(e -> {
            try {
                backgroundLabel.setIcon(returnBackgroundGIF(new URL("http://komotoz.ru/gifki/images/gifki_novyj_god/elka.gif")));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            this.fullBackgroundButton.setEnabled(true);
        });

        this.fullBackgroundButton.addActionListener(e -> {
            try {
                Background background = new Background(returnBackgroundGIF(new URL("http://komotoz.ru/gifki/images/gifki_novyj_god/elka.gif")));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        });


//        assert false;
//        audioIn[0] = AudioSystem.getAudioInputStream(new File("/home/artyom/Загрузки/audio.wav"));
//        assert false;
//        clip[0] = AudioSystem.getClip();
//        clip[0].open(audioIn[0]);
//        clip[0].start();
//        this.stopButton.setEnabled(true);

        this.playButton.addActionListener(e -> {
            if (clip[0] == null) {
                try {
                    audioIn[0] = AudioSystem.getAudioInputStream(new File(returnCurrentAudio((String) Objects.requireNonNull(musicComboBox.getSelectedItem()))));
                    clip[0] = AudioSystem.getClip();
                    clip[0].open(audioIn[0]);
                    clip[0].start();
                    this.stopButton.setEnabled(true);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            } else {
                if (clip[0].isRunning()) {
                    timeMusic.set(clip[0].getMicrosecondPosition());
                    clip[0].stop();
                    this.stopButton.setEnabled(false);
                } else {
                    clip[0].setMicrosecondPosition(timeMusic.get());
                    clip[0].start();
                    this.stopButton.setEnabled(true);
                }
            }
            clip[0].addLineListener(event -> {
                updateState();
            });
        });

        this.stopButton.addActionListener(e -> {
            clip[0].stop();
            timeMusic.set(0);
            clip[0].setMicrosecondPosition(timeMusic.get());
            this.stopButton.setEnabled(false);
        });

    }

    private void updateState() {
        int frame = clip[0].getFramePosition();
        int progress = (int) (((double) frame / (double) audioIn[0].getFrameLength()) * 100);
        musicSlider.setValue(progress);
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

    public ImageIcon returnBackgroundImage(URL url) {
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

    public ImageIcon returnBackgroundGIF(URL url) {
        backgroundLabel.setText(null);
        ImageIcon imageIcon = new ImageIcon(url);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(backgroundLabel.getParent().getWidth(), backgroundLabel.getParent().getHeight(), Image.SCALE_DEFAULT));

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

    private String returnCurrentAudio(String current) {
        String returnable = null;
        switch (current) {
            case "Тестовая музыка":
                returnable = "/home/artyom/Загрузки/audio.wav";
        }
        return returnable;
    }

}
