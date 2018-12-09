package cz.uhk.fim.rssreader.gui;


import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.model.RssList;
import cz.uhk.fim.rssreader.utils.FileUtils;
import cz.uhk.fim.rssreader.utils.RssParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE = "IO_SAVE_TYPE";

    private JPanel panel;
    private JPanel btnPanel;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    public JComboBox searchField;
    private JPanel content;
    private JTextPane rssField;

    private JLabel lblError;
    private RssList rssList;
    private RSSSource rssSource;
    private List<RSSSource> rssSources;

    public MainFrame() {
        init();
    }

    //TODO - dialog: - 2 fieldy (název, link) - pro oba validace (validateInput - upravit metodu pro potřeby kódu)
    //      - validace polí "název" a "link" na přítomnost středníku (replaceAll(";", "");)
    // - přidat do/upravit GUI - tlačítka "Add", "Edit", "Remove/Delete" - pro CRUD akce se sources
    //      - přidat ComboBox pro výběr zdroje feedu - pouze název feedu (bez linku)
    // - tlačítko "Load" - volitelně - buď automatická změna při výběru v ComboBoxu nebo výběr v ComboBoxu a pak Load
    // - aplikace bude fungovat jak pro lokální soubor, tak pro online feed z internetu
    // - aplikace v žádném případě nespadne na hubu - otestovat a ošetřit
    // - funkční ukládání a načítání konfigurace
    // - při spuštění aplikace se automaticky načte první záznam z konfigurace
    //          - pokud konfigurace existuje nebo není prázdná -> nutno kontrolovat

    private void init() {
        setTitle("RSS reader");
        setSize(800, 600);
        // setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

//        ImageIcon img = new ImageIcon("C:\\Users\\brandmi\\IdeaProjects\\RSSreader\\src\\cz\\uhk\\fim\\rssreader\\resource\\icon.png"); //todo

        //init polozek
        panel = new JPanel(new BorderLayout());
        btnPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        searchField = new JComboBox();
        content = new JPanel(new WrapLayout());

        rssField = new JTextPane();
        rssField.setContentType("text/html");
        lblError = new JLabel("info: ");
        lblError.setForeground(Color.RED);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setVisible(false);

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        panel.add(lblError, BorderLayout.NORTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        loadLinks();
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(rssField), BorderLayout.CENTER);


        add(new JScrollPane(content), BorderLayout.CENTER);


        searchField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    loadRssItems(((RSSSource) e.getItem()).getSource());
                    rssSource = ((RSSSource) e.getItem());
                    System.out.println("____");
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rssSources.remove(rssSource);
                FileUtils.saveSource(rssSources);
                loadLinks();
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DetailWindow detailWindow = new DetailWindow(rssSources);
                detailWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadLinks();
                    }

                });
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rssSource != null) {
                    DetailWindow detailWindow = new DetailWindow(rssSources, rssSources.indexOf(rssSource));
                    detailWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            loadLinks();
                        }

                    });
                }
            }
        });
    }

    private void loadRssItems(String url) {
        System.out.println(url);
        if (url != null)
            try {
                rssList = new RssParser().getParsedRSS(url);
//            rssList = new RssParser().getParsedRSS("rss.xml");
                changeContent();
            } catch (Exception e) {
                showErrorMessage(IO_LOAD_TYPE);
            }
    }

    public void changeContent() {
        content.removeAll();
        for (RSSItem item : rssList.getAll()) {
            if (!item.isSeen())
                content.add(new CardVIew(item, this));
            System.out.println(item.getPudDate());
            repaint();
        }
        content.revalidate();
        lblError.setVisible(false);
    }

    private void loadLinks() {
        rssSources = FileUtils.loadSource();
        searchField.removeAllItems();

        for (RSSSource source : rssSources) {
            searchField.addItem(source);
        }

        if (rssSources.size() > 0) {
            loadRssItems(rssSources.get(0).getSource());
            rssSource = rssSources.get(0);
        } else {
            content.removeAll();
        }
    }

//    private boolean validateInput() {
//        if (searchField.getText().trim().isEmpty()) {
//            showErrorMessage(VALIDATION_TYPE);
//            return false;
//        }
//        lblError.setVisible(false);
//        return true;
//    }

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

    public JComboBox getSearchField() {
        return searchField;
    }

    public MainFrame setSearchField(JComboBox searchField) {
        this.searchField = searchField;
        return this;
    }
}
