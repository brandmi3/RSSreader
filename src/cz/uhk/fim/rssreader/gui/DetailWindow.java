package cz.uhk.fim.rssreader.gui;

import javax.swing.*;
import java.awt.*;

public class DetailWindow extends JFrame {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;

    private JPanel panel;
    private JLabel lblName;
    private JLabel lblUrl;
    private JTextField txtName;
    private JTextField txtUrl;

    public DetailWindow() {
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        lblName = new JLabel("Název");
        lblUrl = new JLabel("Adresa");
        txtName = new JTextField();
        txtName.setSize(20,20);
        txtUrl = new JTextField();
        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblUrl);
        panel.add(txtUrl);

        add(panel);
    }
}
