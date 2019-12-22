package com.artyomgeta.newyear;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


@SuppressWarnings({"unused", "rawtypes", "NonAsciiCharacters", "DuplicatedCode"})
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
    private JButton следующийВопросButton;
    private JTextField textField2;
    private JButton показатьButton;
    private JTextArea textArea1;
    private JButton fullBackgroundButton;
    int teamLength = returnTeamsLength();
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
    int[] teamPoints = new int[returnTeamsLength()];
    private JLabel[] teamPointsLabel;
    private JMenuItem changeURLTeamItem = new JMenuItem("Изменить ссылки");
    private JMenuItem watchInfoTeamItem = new JMenuItem("Посмотреть информацию");
    private JMenu addSomethingMenu = new JMenu("Добавить ресурс...");
    private JMenuItem addBackgroundMenuItem = new JMenuItem("Добавить фон");
    private JMenuItem addToTeamItem = new JMenuItem("Добавить балл");
    int questionLength = returnQuestionsLength();
    private JMenuItem removeFromTeamItem = new JMenuItem("Удалить балл");
    private JMenu[] teamsMenu = new JMenu[returnTeamsLength()];
    private JToolBar closeToolBar;
    private JToolBar bottomToolBar;
    private JPanel[] teamsPanel = new JPanel[teamLength];
    private JMenu timeMenu = new JMenu("Время...");
    private JMenuItem continueMenuItem = new JMenuItem("Продолжить");
    private JMenuItem pauseMenuItem = new JMenuItem("Пауза");
    private JMenu viewMenu = new JMenu("Вид...");
    private JMenuItem setToolBarInvisibleItem = new JMenuItem("Скрыть меню");
    private JMenuItem setFullScreenItem = new JMenuItem("Растянуть на весь экран");
    private JMenuItem setBottomToolBarInvisible = new JMenuItem("Скрыть меню статуса");
    private short[] team_points = {0};
    private JMenuItem exitItem = new JMenuItem("Выйти");
    private int workingMinutes = 0;
    private int workingSeconds = 0;
    private JButton closeButton;
    private JPanel teamPanel;
    private JComboBox teamComboBox;
    private JButton addPointButton;
    private JLabel[] teamNameLabel = new JLabel[returnTeamsLength()];

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

        System.out.println(returnQuestionsLength());
        System.out.println(returnTeamsLength());
        teamLength = returnTeamsLength();
        teamPointsLabel = new JLabel[teamLength];

        this.setJMenuBar(menuBar);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.teamMenu);
        this.menuBar.add(this.actionMenu);

//        this.teamMenu.add(this.teamsMenu);
//
//        this.teamsMenu.add(this.addToTeamItem);
//        this.teamsMenu.add(this.removeFromTeam1Item);
//        this.teamsMenu.add(this.watchInfoTeamItem);
//        this.teamsMenu.add(this.changeURLTeamItem);


        this.removeFromTeamItem.addActionListener(e -> System.out.println(e.getActionCommand()));
        this.addToTeamItem.addActionListener(e -> System.out.println(e.getActionCommand()));

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

        this.teamPanel.setLayout(new GridLayout());

        for (int i = 0; i < returnTeamsLength(); i++) {
            this.teamsPanel[i] = new JPanel();
            this.teamNameLabel[i] = new JLabel(returnTeamName(i));
            this.teamPanel.add(this.teamsPanel[i]);
            this.teamsPanel[i].setBorder(BorderFactory.createBevelBorder(1));
            this.teamsPanel[i].add(this.teamNameLabel[i]);
            this.teamNameLabel[i].setFont(new Font("Times New Roman", Font.PLAIN, 30));
            this.teamNameLabel[i].setHorizontalAlignment(0);

            int finalI = i;
            this.teamsPanel[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    teamsPanel[finalI].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    teamsPanel[finalI].setBorder(BorderFactory.createBevelBorder(1));
                }
            });

            this.teamsMenu[i] = new JMenu(returnTeamName(i));
            this.teamMenu.add(this.teamsMenu[i]);
            this.teamsMenu[i].add(this.addToTeamItem);
            this.teamsMenu[i].add(this.removeFromTeamItem);
            this.teamsMenu[i].add(this.watchInfoTeamItem);
            this.teamsMenu[i].add(this.changeURLTeamItem);

            addToTeamItem.addActionListener(e -> teamPoints[finalI]++);
            removeFromTeamItem.addActionListener(e -> teamPoints[finalI]--);

            this.teamsPanel[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(finalI);
                    teamsPanel[finalI].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    teamPoints[finalI]++;
                    teamPointsLabel[finalI].setText("" + teamPoints[finalI]);
                }
            });
            this.teamsPanel[i].setLayout(new GridLayout(3, 1));
            this.teamPointsLabel[i] = new JLabel(0 + "");
            this.teamsPanel[i].add(this.teamPointsLabel[i]);
            this.teamPointsLabel[i].setHorizontalAlignment(0);
            this.teamPointsLabel[i].setFont(new Font("Times New Roman", Font.BOLD, 90));
        }

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
            clip[0].addLineListener(event -> updateState());
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


    private String returnCurrentAudio(String current) {
        String returnable = null;
        if ("Тестовая музыка".equals(current)) {
            returnable = "/home/artyom/Загрузки/audio.wav";
        }
        return returnable;
    }

    private int returnQuestionsLength() {
        StringBuilder sb = new StringBuilder();
        int length = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/artyom/Документы/Вопросы.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            length = jsonArray.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    private int returnTeamsLength() {
        StringBuilder sb = new StringBuilder();
        int length = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/artyom/Документы/Комманды.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            length = jsonArray.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    private String returnTeamName(int team) {
        StringBuilder sb = new StringBuilder();
        String name = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/artyom/Документы/Комманды.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            JSONObject jsonObject;
            jsonObject = jsonArray.getJSONObject(team);
            name = jsonObject.getString("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

}
