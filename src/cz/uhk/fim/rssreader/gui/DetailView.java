package cz.uhk.fim.rssreader.gui;

import com.sun.deploy.util.BlackList;
import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailView extends JFrame {

    private final static int WIDTH = 350;
    private final static int HEIGHT = 450;

    private final static int COMPONENT_WIDTH = 350;
    private final static int COMPONENT_HEIGHT = 1;

    private JPanel panel;

    final String startTag = "<html><p style='width: " + COMPONENT_WIDTH + " px'>";
    final String endTag = "</p></html>";

    public DetailView(RSSItem item, Color color) {
        init(item, WIDTH, HEIGHT, color);
    }

    private void init(RSSItem item, int width, int heigth, Color color) {
        setLayout(new WrapLayout());
        setSize(width, heigth);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel(new WrapLayout());
        panel.setSize(COMPONENT_WIDTH, COMPONENT_HEIGHT);
        addTitle(item.getTitle());
        addDescription(item.getDescription());
        addAuthor(item.getAuthor());
        addDate(item.getPudDate());
        addLink(item.getLink(),color);
        add(panel);
        //panel.setBackground(color);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    dispose();
            }
        });
        pack();
    }

    private void addTitle(String s) {
        JLabel lbl = new JLabel(s);
        lbl.setSize(COMPONENT_WIDTH, COMPONENT_HEIGHT);
        lbl.setVerticalAlignment(0);
        lbl.setHorizontalAlignment(0);
        lbl.setBackground(Color.RED);
        lbl.setFont(new Font("Courier", Font.BOLD, 18));
        lbl.setText(String.format("%s%s%s", startTag, s, endTag));
        panel.add(lbl);
    }

    private void addAuthor(String s) {
        s = s!=null?s:"";
        JLabel lbl = new JLabel(s);
        lbl.setSize(COMPONENT_WIDTH, COMPONENT_HEIGHT);
        lbl.setFont(new Font("Courier", Font.PLAIN, 16));
        lbl.setText(String.format("%s%s%s", startTag, s, endTag));
        panel.add(lbl);
    }

    private void addDescription(String s) {
        JLabel lbl = new JLabel(s);
        lbl.setSize(COMPONENT_WIDTH, COMPONENT_HEIGHT);
        lbl.setFont(new Font("Courier", Font.PLAIN, 16));
        lbl.setText(String.format("%s%s%s", startTag, s, endTag));
        panel.add(lbl);
    }

    private void addLink(String s,Color color) {
        JButton btnLink = new JButton("Open in browser");
        btnLink.setSize(COMPONENT_WIDTH / 2, COMPONENT_HEIGHT);
        btnLink.setBackground(color);
        btnLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    openWebpage(new URL(s).toURI());
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JButton btnLink2 = new JButton("Copy link into clipboard");
        btnLink2.setSize(COMPONENT_WIDTH / 2, COMPONENT_HEIGHT);
        btnLink2.setBackground(color);
        btnLink2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    setClipboardContents(s);
                    btnLink2.setText("Copied");
                } catch (Exception e1) {
                    btnLink2.setText("Copy failed");
                }
            }
        });
        panel.add(btnLink);
        panel.add(btnLink2);
    }

    private void addDate(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        JLabel lbl = new JLabel(dateFormat.format(new Date(s)));

        lbl.setSize(COMPONENT_WIDTH, COMPONENT_HEIGHT);
        lbl.setFont(new Font("Courier", Font.PLAIN, 16));
        lbl.setText(String.format("%s%s%s", startTag, s, endTag));
        panel.add(lbl);
    }

    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void setClipboardContents(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
