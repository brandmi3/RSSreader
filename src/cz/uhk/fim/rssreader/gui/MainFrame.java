package cz.uhk.fim.rssreader.gui;


import cz.uhk.fim.rssreader.model.RssList;
import cz.uhk.fim.rssreader.utils.FileUtils;
import cz.uhk.fim.rssreader.utils.RssParser;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE = "IO_SAVE_TYPE";

    private JPanel panel;
    private JButton btnSave;
    private JButton btnLoad;
    private JTextField searchField;
    JTextPane rssField ;

    private JLabel lblError;
    private RssList rssList;


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

        rssField.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                }
                System.out.println(e);
            }
        });

        btnLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent m) {
                if (validateInput()) {
                    rssField.setText("");
//                        rssField.setText(FileUtils.loadStringFromFile(searchField.getText()));
                    rssList = new RssParser().getParsedRSS(searchField.getText());
                    String output = "";
                    for (int i = 0; i < rssList.getAll().size(); i++) {
                        output += rssList.getItem(i).toString() + "\n";
                    }
                    rssField.setText(output);
                    /*try {

                    } catch (IOException e) {
                        lblError.setText("Špatný název!");
                        lblError.setVisible(true);
                    }*/

                }

            }
        });
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (validateInput()) {
                    rssList = new RssParser().getParsedRSS(rssField.getText());
                    rssField.setText("");
                }
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
