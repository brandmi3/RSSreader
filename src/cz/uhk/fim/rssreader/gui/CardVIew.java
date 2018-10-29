package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import java.awt.*;

public class CardVIew extends JPanel {

    private static final int ITEM_WIDTH = 180;
    private static final int COMPONENT_WIDTH = 160;
    private static final int HEIGHT = 1;
    final String startTag = "<html><p style='width: " + COMPONENT_WIDTH + " px'>";
    final String endTag = "</p></html>";


    public CardVIew(RSSItem item) {
        setLayout(new WrapLayout());
        setSize(ITEM_WIDTH, HEIGHT);
        setTitle(item.getTitle());
        setDescription(item.getDescription());
        setBackground(new Color(0xffffff - (item.getDescription().length()+item.getTitle().length())*1000));//todo omezit
        setAdditionalInfo(String.format("%s - %s", item.getAuthor(), item.getPudDate()));
    }

    private int computeColor(String s) {

       return 0;
    }

    private void setTitle(String s) {
        JLabel lblTitle = new JLabel(s);
        lblTitle.setSize(COMPONENT_WIDTH, HEIGHT);
        lblTitle.setFont(new Font("Courier", Font.BOLD, 20));
        lblTitle.setText(String.format("%s%s%s", startTag, s, endTag));
        add(lblTitle);
    }

    private void setDescription(String s) {
        JLabel lblDesc = new JLabel(s);
        lblDesc.setSize(COMPONENT_WIDTH, HEIGHT);
        lblDesc.setFont(new Font("Courier", Font.PLAIN, 16));
        lblDesc.setText(String.format("%s%s%s", startTag, s, endTag));
        add(lblDesc);
    }

    private void setAdditionalInfo(String s) {
        JLabel lblInfo = new JLabel(s);
        lblInfo.setSize(COMPONENT_WIDTH, HEIGHT);
        lblInfo.setFont(new Font("Courier", Font.PLAIN, 16));
        lblInfo.setForeground(Color.LIGHT_GRAY);
        lblInfo.setText(String.format("%s%s%s", startTag, s, endTag));
        add(lblInfo);
    }

}
