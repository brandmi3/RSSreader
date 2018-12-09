package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardVIew extends JPanel {

    private static final int ITEM_WIDTH = 180;
    private static final int COMPONENT_WIDTH = 160;
    private static final int HEIGHT = 1;
    private static final int DESCRIPTION_LENGTH = 120;

    final String startTag = "<html><p style='width: " + COMPONENT_WIDTH + " px'>";
    final String endTag = "</p></html>";
    private static DetailView detailView;

    public CardVIew(RSSItem item, MainFrame mainFrame) {
        setLayout(new WrapLayout());
        setSize(ITEM_WIDTH, HEIGHT);
        setTitle(item.getTitle());
        setDescription(item.getDescription());
        setBackground(new Color(0xffffff - (item.getDescription().length() + item.getTitle().length()) * 9000));
        setAdditionalInfo(item.getAuthor() != null ? item.getAuthor() : "", item.getPudDate());
        JButton btn_preceteno = new JButton("Přečteno");
        add(btn_preceteno);
        btn_preceteno.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                item.setSeen(true);
                System.out.println(item);
                mainFrame.changeContent();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (detailView != null) {
                        detailView.dispose();
                    }
                    detailView = new DetailView(item, getBackground());
                }
            }
        });
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
        if (s.length() > DESCRIPTION_LENGTH)
            s = formatDesc(s);
        lblDesc.setSize(COMPONENT_WIDTH, HEIGHT);
        lblDesc.setFont(new Font("Courier", Font.PLAIN, 16));
        lblDesc.setText(String.format("%s%s%s", startTag, s, endTag));
        add(lblDesc);
    }

    private String formatDesc(String s) {
        return s.substring(0, DESCRIPTION_LENGTH) + " ...";
    }

    private void setAdditionalInfo(String a,String d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        a = a != null ? a : "";
        String out = a;
        try{
             String date = dateFormat.format(new Date (d));
             out += "\n"+ date;
        }catch (Exception e){
            System.out.println("chyba při editaci datumu.");
        }
        JLabel lblInfo = new JLabel(out);
        lblInfo.setSize(COMPONENT_WIDTH, HEIGHT);
        lblInfo.setFont(new Font("Courier", Font.PLAIN, 16));
        lblInfo.setForeground(Color.DARK_GRAY);
        lblInfo.setText(String.format("%s%s%s", startTag, out, endTag));
        add(lblInfo);
    }

}
