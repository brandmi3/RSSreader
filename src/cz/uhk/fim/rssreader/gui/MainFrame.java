package cz.uhk.fim.rssreader.gui;


import cz.uhk.fim.rssreader.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainFrame extends JFrame {


    private JPanel panel;
    private JButton btnSave;
    private JButton btnLoad;
    private JTextField searchField;
    private JTextArea rssField;
    private JLabel lblError;

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
        lblError= new JLabel("info: ");

        panel.add(btnLoad, BorderLayout.WEST);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(btnSave, BorderLayout.EAST);
        panel.add(lblError,BorderLayout.SOUTH);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(rssField), BorderLayout.CENTER);

        btnLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent m) {

                System.out.println("load");
                try {
                    rssField.setText(FileUtils.loadStringFromFile(searchField.getText()));//rss_sport.xml
                } catch (IOException e) {
                    searchField.setBackground(Color.pink);
                    lblError.setText("info: "+e);
                }
            }
        });
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println("save");
            }
        });

//todo napsat metodu validateInpu
//todo pridat JLabel -> Error - red Color


    }
}
