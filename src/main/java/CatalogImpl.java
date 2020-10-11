
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class CatalogImpl implements Catalog {

    private static final String FILE_NAME = "Catalog.xml";
    public static final String DATABASE_TAG = "Database";
    public static  final String NAME_TAG="name";
    public static  final String DATABASES_TAG="Databases";
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;

    Node databasesElement;


    public CatalogImpl() {

        this.loadXML();
       // databasesElement = (Element) doc.getElementsByTagName(DATABASES_TAG).item(0);

        databasesElement=doc.getFirstChild();

    }

    public void loadXML() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            doc = dBuilder.parse(FILE_NAME);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDatabase(String dbName) {

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = documentBuilder.newDocument();

        Element databaseElement = doc.createElement(DATABASE_TAG);
        doc.appendChild(databaseElement);
        databaseElement.setAttribute(NAME_TAG,dbName);

//        databasesElement.appendChild(databaseElement);
        Element root = doc.createElement("company");
        doc.appendChild(root);

        // employee element
        Element employee = doc.createElement("employee");

        root.appendChild(employee);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(FILE_NAME);

        // If you use
        // StreamResult result = new StreamResult(System.out);
        // the output will be pushed to the standard output ...
        // You can use that for debugging

        try {
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }



    public void dropDatabase(String dbName) {

    }

    public void saveTable(String dbName, String tableName) {

    }

    public void dropTable(String dbName, String tableName) {

    }
}
