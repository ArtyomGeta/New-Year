package com.artyomgeta.newyear;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Team {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JButton закрытьButton;

    public Team(int team) {

    }

    @SuppressWarnings("DuplicatedCode")
    private String returnTeamName(int team) {
        StringBuilder sb = new StringBuilder();
        String name = null;
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
        }
        return name;
    }


}
