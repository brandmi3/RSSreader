package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class DetailView extends JFrame {

    private final static int WIDTH = 500;
    private final static int HEIGHT = 300;

    private final static int COMPONENT_WIDTH = 200;

    private JPanel panel;
    private JPanel contentPanel;

    public DetailView(RSSItem item, Color color) {
        init(item, WIDTH, HEIGHT, color);
    }

    private void init(RSSItem item, int width, int heigth, Color color) {
        setSize(width, heigth);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.RED);

        panel = new JPanel(new BorderLayout());
        contentPanel = new JPanel(new BorderLayout());
        addTitle(item.getTitle());
        addDescription(item.getDescription());
        addAuthor(item.getAuthor());
        addDate(item.getPudDate());
        addLink(item.getLink());
        add(panel);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.setBackground(color);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    dispose();
            }
        });
    }


    private void addTitle(String s) {
        JLabel label = new JLabel(s);
        panel.add(label, BorderLayout.NORTH);
    }

    private void addAuthor(String s) {
        JLabel label = new JLabel(s);
        contentPanel.add(label, BorderLayout.NORTH);
    }

    private void addDescription(String s) {
        JLabel label = new JLabel(s);
        label.setVerticalAlignment(SwingConstants.TOP);
        contentPanel.add(label, BorderLayout.CENTER);
    }

    private void addLink(String s) {
        JLabel label = new JLabel(s);
        contentPanel.add(label, BorderLayout.SOUTH);
    }

    private void addDate(String s) {
        JLabel label = new JLabel(s);
        panel.add(label, BorderLayout.SOUTH);
    }

}
