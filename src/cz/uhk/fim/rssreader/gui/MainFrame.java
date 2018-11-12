package cz.uhk.fim.rssreader.gui;


import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.model.RssList;
import cz.uhk.fim.rssreader.utils.FileUtils;
import cz.uhk.fim.rssreader.utils.RssParser;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE = "IO_SAVE_TYPE";

    private JPanel panel;
    private JButton btnSave;
    private JButton btnLoad;
    private JTextField searchField;
    JTextPane rssField;

    private JLabel lblError;
    private RssList rssList;


    public MainFrame() {
        init();
    }

    private void init() {
        setTitle("RSS reader");
        setSize(800, 600);
        // setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //init polozek
        panel = new JPanel(new BorderLayout());
        btnSave = new JButton("Save");
        btnLoad = new JButton("Load");
        searchField = new JTextField();
        rssField = new JTextPane();
        rssField.setContentType("text/html");
        lblError = new JLabel("info: ");
        lblError.setForeground(Color.RED);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setVisible(false);

        panel.add(btnLoad, BorderLayout.WEST);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(btnSave, BorderLayout.EAST);
        panel.add(lblError, BorderLayout.SOUTH);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(rssField), BorderLayout.CENTER);

        JPanel content = new JPanel(new WrapLayout());

        try {
            rssList = new RssParser().getParsedRSS("https://www.zive.cz/rss/sc-47");
//            rssList = new RssParser().getParsedRSS("rss.xml");

            for (RSSItem item : rssList.getAll()) {
                content.add(new CardVIew(item));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        add(new JScrollPane(content), BorderLayout.CENTER);
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<RSSSource> sources = FileUtils.loadSource();
                for (RSSSource source : sources) {
                    System.out.println(source.getName() + " " + source.getSource());
                }
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<RSSSource> source = new ArrayList<>();
                source.add(new RSSSource("živě.cz", "123"));
                source.add(new RSSSource("živě2.cz", "1"));
                source.add(new RSSSource("živě3.cz", "2"));
                source.add(new RSSSource("živě4.cz", "3"));
                FileUtils.saveSource(source);
            }
        });
    }

    private boolean validateInput() {
        if (searchField.getText().trim().isEmpty()) {
            showErrorMessage(VALIDATION_TYPE);
            return false;
        }
        lblError.setVisible(false);
        return true;
    }

    private void showErrorMessage(String type) {
        String msg;
        switch (type) {
            case VALIDATION_TYPE:
                msg = "Chyba! Pole nesmí být prázdné.";
                break;
            case IO_LOAD_TYPE:
                msg = "Chyba při načítání!";
                break;
            case IO_SAVE:
                msg = "Chyba při ukládán!";
                break;
            default:
                msg = "Chybička!";
                break;
        }
        lblError.setText(msg);
        lblError.setVisible(true);
    }
}
