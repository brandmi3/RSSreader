package cz.uhk.fim.rssreader.utils;

import cz.uhk.fim.rssreader.model.RssList;
import org.xml.sax.SAXException;

import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class RssParser {
    private RssList rssList;
    private ItemHandler itemHandler;

    public RssParser() {
        this.rssList = new RssList();
        this.itemHandler = new ItemHandler(rssList);
    }

    private void parse(String source) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        parser.parse(new File(source), itemHandler);

    }

    public RssList getParsedRSS(String source) {
        try {
            parse(source);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return rssList;
    }
}
