package controller;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * User: Damian
 * Date: 23/11/13
 * Time: 23:47
 */
public class DataWorker {

    private static Document document;

    public static void init() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        openFile("");
        getData("");
    }

    public static void openFile(String filePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = builder.parse(new FileInputStream(filePath));
    }

    public static String getData(String expression) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        return xPath.compile(expression).evaluate(document);
    }
}
