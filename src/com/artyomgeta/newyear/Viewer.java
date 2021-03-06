package com.artyomgeta.newyear;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static javax.swing.JOptionPane.*;


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
    private JComboBox backbroundComboBox;
    private JButton a1Button;
    private JButton nextQuestionButton;
    private JTextField textField2;
    private JButton показатьButton;
    private JButton fullBackgroundButton;
    int teamLength = returnTeamsLength();
    private JLabel backgroundLabel;
    private JPanel backgroundPanel;
    private JButton stopButton;
    private static final int USE_INTERNET_TO_LOAD_RESOURCES_OPTION = 0;
    private JSlider musicSlider;
    private JLabel timeLabel;
    private JTextField textField1;
    private JToolBar mainToolBar;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("Файл");
    private JMenu teamMenu = new JMenu("Команды");
    private JMenu actionMenu = new JMenu("Действие");
    int[] teamPoints = new int[returnTeamsLength()];
    private JComboBox<String> musicComboBox;
    private JLabel[] teamPointsLabel;
    private JMenuItem[] watchInfoTeamItem = new JMenuItem[returnTeamsLength()];
    private JMenuItem addBackgroundMenuItem = new JMenuItem("Добавить фон");
    private JMenuItem[] addToTeamItem = new JMenuItem[returnTeamsLength()];
    int questionLength = returnQuestionsLength();
    private JMenuItem[] removeFromTeamItem = new JMenuItem[returnTeamsLength()];
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
    private JMenuItem exitItem = new JMenuItem("Выйти");
    private String[] audios = new String[returnAudiosLength()];
    private int workingMinutes = 0;
    private int workingSeconds = 0;
    private JPanel teamPanel;
    private JCheckBoxMenuItem useInternetMenuButton = new JCheckBoxMenuItem("Использовать загрузку ресурсов через Интернет");
    private JButton addPointButton;
    private JLabel[] teamNameLabel = new JLabel[returnTeamsLength()];
    private JComboBox<String> teamComboBox;
    private JToolBar.Separator separator1;
    private JToolBar.Separator separator2;
    private JLabel backgroundToolBarLabel;
    private JLabel teamsToolBarLabel;

    private String nowIsPlayingAudioName = null;

    public Viewer() throws java.lang.NullPointerException {
        setUI();
        bufferFiles();
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

        //System.out.println(returnQuestionsLength());
        //System.out.println(returnTeamsLength());
        teamLength = returnTeamsLength();
        teamPointsLabel = new JLabel[teamLength];

        this.setJMenuBar(menuBar);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.teamMenu);
        this.menuBar.add(this.actionMenu);

        this.useInternetMenuButton.addActionListener(e -> {
            try {
                FileWriter fileWriter = new FileWriter(new File("settings.json"));
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                if (!useInternetMenuButton.isSelected())
                    jsonObject.put("use-internet-to-load-resources", false);
                else jsonObject.put("use-internet-to-load-resources", true);
                jsonArray.put(jsonObject);
                fileWriter.write(String.valueOf(jsonArray));
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        this.actionMenu.add(this.timeMenu);
        this.timeMenu.add(this.continueMenuItem);
        this.timeMenu.add(this.pauseMenuItem);
        this.actionMenu.add(this.useInternetMenuButton);
        this.useInternetMenuButton.setSelected(returnSettings(USE_INTERNET_TO_LOAD_RESOURCES_OPTION));

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
        DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();

        for (int i = 0; i < returnTeamsLength(); i++) {
            this.teamsPanel[i] = new JPanel();
            this.teamNameLabel[i] = new JLabel(returnTeamName(i));
            this.teamPanel.add(this.teamsPanel[i]);
            this.teamsPanel[i].setBorder(BorderFactory.createBevelBorder(1));
            this.teamsPanel[i].add(this.teamNameLabel[i]);
            this.teamNameLabel[i].setFont(new Font(this.teamNameLabel[i].getFont().getName(), Font.PLAIN, 30));

            this.teamNameLabel[i].setHorizontalAlignment(0);

            this.addToTeamItem[i] = new JMenuItem("Добавить балл");
            this.removeFromTeamItem[i] = new JMenuItem("Удалить балл");
            this.watchInfoTeamItem[i] = new JMenuItem("Посмотреть информацию о команде");

            int finalI = i;
            this.teamsPanel[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    teamsPanel[finalI].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    teamsPanel[finalI].setBorder(BorderFactory.createBevelBorder(1));
                }

            });

            this.removeFromTeamItem[i].addActionListener(e -> {
                teamPoints[finalI]--;
                teamPointsLabel[finalI].setText("" + teamPoints[finalI]);
            });
            this.addToTeamItem[i].addActionListener(e -> {
                teamPoints[finalI]++;
                teamPointsLabel[finalI].setText("" + teamPoints[finalI]);
            });

            defaultComboBoxModel.addElement(returnTeamName(i));
            this.teamComboBox.setModel(defaultComboBoxModel);
            //System.out.println(i);
            this.teamsMenu[i] = new JMenu(returnTeamName(i));
            this.teamsMenu[i].add(this.addToTeamItem[i]);
            this.teamsMenu[i].add(this.removeFromTeamItem[i]);
            this.teamsMenu[i].add(this.watchInfoTeamItem[i]);
            this.teamMenu.add(this.teamsMenu[i]);


            this.teamsPanel[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        teamsPanel[finalI].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                        teamPoints[finalI]++;
                        teamPointsLabel[finalI].setText("" + teamPoints[finalI]);
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        if (teamPoints[finalI] > 0) {
                            teamsPanel[finalI].setBorder(BorderFactory.createLineBorder(Color.RED));
                            teamPoints[finalI]--;
                            teamPointsLabel[finalI].setText("" + teamPoints[finalI]);
                        }
                    } else if (e.getButton() == MouseEvent.BUTTON2) {
                        String resultString = showInputDialog(null, "Введите число для добавлнеия", "Ввод", QUESTION_MESSAGE);
                        int result = 0;
                        try {
                            result = Integer.parseInt(resultString);
                        } catch (NumberFormatException ex) {
                            showMessageDialog(null, "Введите число!", "Ошибка", ERROR_MESSAGE);
                        }
                        teamPoints[finalI] += result;
                        teamPointsLabel[finalI].setText(teamPoints[finalI] + "");
                    }
                }
            });
            this.teamsPanel[i].setLayout(new GridLayout(3, 1));
            this.teamPointsLabel[i] = new JLabel(0 + "");
            this.teamsPanel[i].add(this.teamPointsLabel[i]);
            this.teamPointsLabel[i].setHorizontalAlignment(0);
            this.teamPointsLabel[i].setFont(new Font(this.teamPointsLabel[i].getFont().getName(), Font.BOLD, 120));
        }

        for (int i = 0; i < returnAudiosLength(); i++) {
            this.audios[i] = returnAudioName(i);
            DefaultComboBoxModel<String> defaultMusicComboBoxModel = new DefaultComboBoxModel<>(audios);
            this.musicComboBox.setModel(defaultMusicComboBoxModel);
        }

        this.exitItem.addActionListener(e -> {
            int result = showConfirmDialog(this, "Вы уверены, что хотите выйти?", "Подтвердите", YES_NO_OPTION, QUESTION_MESSAGE);
            if (result == YES_NO_OPTION) {
                System.exit(1);
            }
        });

        this.teamMenu.setEnabled(true);
        this.actionMenu.setEnabled(true);

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


        this.tabbedPane1.addChangeListener(e -> {
            if (tabbedPane1.getSelectedIndex() == 0) {
                this.separator1.setVisible(false);
                this.backgroundToolBarLabel.setVisible(false);
                this.setBackgroundButton.setVisible(false);
                this.fullBackgroundButton.setVisible(false);
                this.backbroundComboBox.setVisible(false);

                this.separator2.setVisible(true);
                this.teamsToolBarLabel.setVisible(true);
                this.addPointButton.setVisible(true);
                this.teamComboBox.setVisible(true);
                this.nextQuestionButton.setVisible(true);
            } else {
                this.separator1.setVisible(true);
                this.backgroundToolBarLabel.setVisible(true);
                this.setBackgroundButton.setVisible(true);
                this.fullBackgroundButton.setVisible(true);
                this.backbroundComboBox.setVisible(true);

                this.separator2.setVisible(false);
                this.teamsToolBarLabel.setVisible(false);
                this.addPointButton.setVisible(false);
                this.teamComboBox.setVisible(false);
                this.nextQuestionButton.setVisible(false);
            }
        });

        this.playButton.addActionListener(e -> {
            if (clip[0] == null) {
                // Первый раз запускаем музыку.
                try {
                    audioIn[0] = AudioSystem.getAudioInputStream(new File("audio/" + returnCurrentAudio((String) musicComboBox.getSelectedItem()) + ".wav"));
                    clip[0] = AudioSystem.getClip();
                    clip[0].open(audioIn[0]);
                    clip[0].start();
                    this.stopButton.setEnabled(true);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | NullPointerException ex) {
                    try {
                        FileUtils.copyURLToFile(new URL(returnAudioSource(musicComboBox.getSelectedIndex())), new File("audio/" + musicComboBox.getSelectedItem()));
                    } catch (IOException exe) {
                        try {
                            audioIn[0] = AudioSystem.getAudioInputStream(new File("audio/" + musicComboBox.getSelectedItem()));
                            clip[0] = AudioSystem.getClip();
                            clip[0].open(audioIn[0]);
                            clip[0].start();
                            this.stopButton.setEnabled(true);
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exc) {
                            exc.printStackTrace();
                        }
                        exe.printStackTrace();
                    }
                }
                nowIsPlayingAudioName = (String) musicComboBox.getSelectedItem();
            } else if (nowIsPlayingAudioName.equals(musicComboBox.getSelectedItem()) && !clip[0].isRunning()) {
                // Была пауза. Продолжаем играть.
                timeMusic.set(clip[0].getMicrosecondPosition());
                clip[0].start();
                this.stopButton.setEnabled(true);
            } else if (!nowIsPlayingAudioName.equals(musicComboBox.getSelectedItem()) && clip[0].isRunning()) {
                // Что-то играло. Включаем другое.
                clip[0].stop();
                clip[0].close();
                try {
                    audioIn[0] = AudioSystem.getAudioInputStream(new File("audio/" + returnCurrentAudio((String) musicComboBox.getSelectedItem()) + ".wav"));
                    clip[0].open(audioIn[0]);
                    clip[0].start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
                nowIsPlayingAudioName = (String) musicComboBox.getSelectedItem();
                stopButton.setEnabled(true);
            } else if (nowIsPlayingAudioName.equals(musicComboBox.getSelectedItem()) && clip[0].isRunning()) {
                // Ставим на паузу.
                timeMusic.set(clip[0].getMicrosecondPosition());
                clip[0].stop();
                stopButton.setEnabled(false);
            } else if (!nowIsPlayingAudioName.equals(musicComboBox.getSelectedItem()) && !clip[0].isRunning()) {
                // Стояла на паузе. Мы включили другое.
                try {
                    clip[0].close();
                    audioIn[0] = AudioSystem.getAudioInputStream(new File("audio/" + returnCurrentAudio((String) musicComboBox.getSelectedItem()) + ".wav"));
                    clip[0].open(audioIn[0]);
                    clip[0].start();
                    nowIsPlayingAudioName = (String) musicComboBox.getSelectedItem();
                    stopButton.setEnabled(true);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.stopButton.addActionListener(e -> {
            try {
                audioIn[0].close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            clip[0].stop();
            timeMusic.set(0);
            clip[0].close();
            clip[0].setMicrosecondPosition(0);
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
        ImageIcon imageIcon;
        if (returnSettings(USE_INTERNET_TO_LOAD_RESOURCES_OPTION)) {
            backgroundLabel.setText(null);
            imageIcon = new ImageIcon(url);
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(backgroundLabel.getParent().getWidth(), backgroundLabel.getParent().getHeight(), Image.SCALE_DEFAULT));
            return imageIcon;
        } else {
            imageIcon = new ImageIcon(String.valueOf(new File("background/elka.gif")));
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(backgroundLabel.getParent().getWidth(), backgroundLabel.getParent().getHeight(), Image.SCALE_DEFAULT));
            backgroundLabel.setText(null);
        }
        return imageIcon;
    }


    private String returnCurrentAudio(String current) {
        StringBuilder sb = new StringBuilder();
        String returnable = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Audios.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("name").equals(musicComboBox.getSelectedItem())) {
                    return jsonArray.getJSONObject(i).getString("name");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnable;
    }

    private void bufferFiles() {
        if (returnSettings(USE_INTERNET_TO_LOAD_RESOURCES_OPTION)) {
            System.out.println("Buffering started:");
            for (int i = 0; i < returnAudiosLength(); i++) {
                try {
                    FileUtils.copyURLToFile(new URL(returnAudioSource(i)), new File("audio/" + returnAudioName(i) + ".wav"));
                    System.out.println(returnAudioSource(i) + " to " + new File("audio/" + returnAudioName(i)).getAbsolutePath() + ".wav");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Buffering completed successful!");
        }
    }

    private int returnQuestionsLength() {
        StringBuilder sb = new StringBuilder();
        int length = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Questions.json"));
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

    private int returnAudiosLength() {
        StringBuilder sb = new StringBuilder();
        int length = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Audios.json"));
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
            BufferedReader br = new BufferedReader(new FileReader("Teams.json"));
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
            showMessageDialog(this, "Не найден файл Teams.json", "Ошибка", ERROR_MESSAGE);
            System.exit(0);
        }
        return length;
    }

    private String returnTeamName(int team) {
        StringBuilder sb = new StringBuilder();
        String name = null;
        if (returnSettings(USE_INTERNET_TO_LOAD_RESOURCES_OPTION)) {
            try {
                FileUtils.copyURLToFile(new URL("https://artyomgeta.github.io/New-Year/Teams.json"), new File("Teams.json"));
            } catch (IOException e) {
                showMessageDialog(this, "Не найден файл Teams.json", "Ошибка", ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("Teams.json"));
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
            showMessageDialog(this, "Не найден файл Teams.json", "Ошибка", ERROR_MESSAGE);
        }
        return name;
    }

    private String returnAudioName(int audio) {
        StringBuilder sb = new StringBuilder();
        String name = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Audios.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            JSONObject jsonObject;
            jsonObject = jsonArray.getJSONObject(audio);
            name = jsonObject.getString("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String returnAudioSource(int audio) {
        StringBuilder sb = new StringBuilder();
        String sourse = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Audios.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            JSONObject jsonObject;
            jsonObject = jsonArray.getJSONObject(audio);
            sourse = jsonObject.getString("src");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourse;
    }

    @SuppressWarnings("SameParameterValue")
    private boolean returnSettings(int option) {
        StringBuilder sb = new StringBuilder();
        boolean returnable = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader("settings.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            JSONObject jsonObject;
            if (option == USE_INTERNET_TO_LOAD_RESOURCES_OPTION) {
                jsonObject = jsonArray.getJSONObject(0);
                returnable = jsonObject.getBoolean("use-internet-to-load-resources");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnable;
    }

}
