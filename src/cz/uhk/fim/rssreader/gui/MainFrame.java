package cz.uhk.fim.rssreader.gui;


import cz.uhk.fim.rssreader.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {


    private JPanel panel;
    private JButton btnSave;
    private JButton btnLoad;
    private JTextField searchField;
    private JTextArea rssField;

    public MainFrame() {
        init();
    }

    private void init() {
        setTitle("RSS reader");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //init polozek
        panel = new JPanel(new BorderLayout());
        btnSave = new JButton("Save");
        btnLoad = new JButton("Load");
        searchField = new JTextField();
        rssField = new JTextArea();

        panel.add(btnLoad, BorderLayout.WEST);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(btnSave, BorderLayout.EAST);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(rssField), BorderLayout.CENTER);

        try {
            rssField.setText(FileUtils.loadStringFromFile("rss_sport.xml"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
