package com.university.project;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Window extends JFrame {
    private Logic logic = new Logic();
    private WorkingWithFiles workingWithFiles = new WorkingWithFiles();
    public Window (String title) {
        super(title);
        createGUI();
    }

    public void createGUI() {
        Dimension SCREEN_RESOLUTION = Toolkit.getDefaultToolkit().getScreenSize();
        int WINDOW_WIDTH = SCREEN_RESOLUTION.width / 2;
        int WINDOW_HEIGHT = SCREEN_RESOLUTION.height / 2;

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton importBtn = new JButton("Открыть файл");
        JButton searchBtn = new JButton("Поиск");

        JTextField searchBar = new JTextField(20);
        JLabel offerToEnterLabel = new JLabel("Введите слово: ");
        JLabel nameFile = new JLabel();

        JTextArea mainTextArea = new JTextArea();
        JTextArea topListArea = new JTextArea(0,30);
        mainTextArea.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        topListArea.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

        mainTextArea.setEnabled(false);
        topListArea.setEnabled(false);
        mainTextArea.setDisabledTextColor(Color.BLACK);
        topListArea.setDisabledTextColor(Color.BLACK);

        JScrollPane textPaneScrollPane = new JScrollPane(mainTextArea);
        JScrollPane topListAreaScrollPane = new JScrollPane(topListArea);
        textPaneScrollPane.setBorder(null);
        topListAreaScrollPane.setBorder(null);

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(5,5));

        JPanel jPanelNorth = new JPanel();
        jPanelNorth.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        jPanelNorth.add(importBtn);
        jPanelNorth.add(offerToEnterLabel);
        jPanelNorth.add(searchBar);
        jPanelNorth.add(searchBtn);

        JPanel jPanelSouth = new JPanel();
        jPanelSouth.add(nameFile);

        container.add(jPanelNorth, BorderLayout.NORTH);
        container.add(jPanelSouth, BorderLayout.SOUTH);
        container.add(textPaneScrollPane, BorderLayout.CENTER);
        container.add(topListAreaScrollPane, BorderLayout.EAST);

        importBtn.addActionListener(e -> {
            workingWithFiles.importFile(this, mainTextArea, nameFile);
            topListArea.setText("");
        });

        searchBtn.addActionListener(e -> {
            if(mainTextArea.getText().length() != 0) {
                logic.sendRequest(searchBar.getText(), mainTextArea, topListArea);
            } else {
                JOptionPane.showMessageDialog(this, "Откройте файл с текстом!");
            }
        });
    }
}
