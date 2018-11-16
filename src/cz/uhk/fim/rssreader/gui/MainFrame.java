package cz.uhk.fim.rssreader.gui;


import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.model.RssList;
import cz.uhk.fim.rssreader.utils.FileUtils;
import cz.uhk.fim.rssreader.utils.RssParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private JComboBox searchField;
    private JPanel content;
    private JTextPane rssField;

    private JLabel lblError;
    private RssList rssList;


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

        ImageIcon img = new ImageIcon("C:\\Users\\brandmi\\IdeaProjects\\RSSreader\\src\\cz\\uhk\\fim\\rssreader\\resource\\icon.png"); //todo
        setIconImage(img.getImage());

        //init polozek
        panel = new JPanel(new BorderLayout());
        btnPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        searchField = new JComboBox();
        content = new JPanel(new WrapLayout());
        loadLinks();

        rssField = new JTextPane();
        rssField.setContentType("text/html");
        lblError = new JLabel("info: ");
        lblError.setForeground(Color.RED);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setVisible(false);
        new DetailWindow();
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        panel.add(searchField, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(rssField), BorderLayout.CENTER);


        add(new JScrollPane(content), BorderLayout.CENTER);

        searchField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    changeContent(((RSSSource) e.getItem()).getSource());
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadLinks();
            }
        });
        btnAdd.addActionListener(new ActionListener() {
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

    private void changeContent(String url) {
        System.out.println(url);
        if (url != null)
            try {
                rssList = new RssParser().getParsedRSS(url);
//            rssList = new RssParser().getParsedRSS("rss.xml");
                content.removeAll();
                for (RSSItem item : rssList.getAll()) {
                    content.add(new CardVIew(item));
                }
                content.revalidate();
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    private void loadLinks() {
        List<RSSSource> sources = FileUtils.loadSource();
        searchField.removeAllItems();

        for (RSSSource source : sources) {
            searchField.addItem(source);
        }

        if (sources.size() > 0) {
            changeContent(sources.get(0).getSource());
        } else {
            changeContent("");
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
}
