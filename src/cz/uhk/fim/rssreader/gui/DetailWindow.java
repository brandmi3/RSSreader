package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DetailWindow extends JFrame {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    private JLabel name;
    private JTextField fldname;
    private JLabel url;
    private JTextField fldurl;
    private JPanel panel;
    private JButton add;
    private JPanel buttons;

    public String nazev;
    public String urls;

    private RSSSource rssSource;
    private List<RSSSource> rssSources;

    public DetailWindow(List<RSSSource> rssSources) throws HeadlessException {
        this.rssSources = rssSources;
        init();
    }

    public DetailWindow(List<RSSSource> rssSources, int i) {
        this.rssSources = rssSources;
        this.rssSource = rssSources.get(i);
        init();
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setVisible(true);

        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gui();
    }

    private void gui() {
        name = new JLabel();
        fldname = new JTextField();
        url = new JLabel();
        fldurl = new JTextField();
        add = new JButton("ULOZIT");
        buttons = new JPanel(new FlowLayout());

        if (rssSource != null) {
            fldname.setText(rssSource.getName());
            fldurl.setText(rssSource.getSource());
        }

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        /*new BoxLayout(panel,BoxLayout.Y_AXIS)*/
        panel.setSize(400, 180);

        add(panel);
        panel.add(name);
        name.setText("Název");
        panel.add(fldname);
        fldname.setPreferredSize(new Dimension(200, 24));
        panel.add(url);
        url.setText("Adresa");
        panel.add(fldurl);
        fldurl.setPreferredSize(new Dimension(200, 24));
        panel.add(buttons);
        buttons.add(add);

        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!fldname.getText().equals("") || !fldurl.getText().equals("")) {
                    if (rssSource == null) {
                        rssSources.add(new RSSSource(fldname.getText(), fldurl.getText()));
                    } else {
                        rssSource.setName(fldname.getText());
                        rssSource.setSource(fldurl.getText());
                    }
                    FileUtils.saveSource(rssSources);
                    dispose();
                }
            }
        });


    }
}
