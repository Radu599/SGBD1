import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
import java.io.IOException;

public class CatalogImpl implements Catalog {

    public static final String FILE_NAME = "Catalog.xml";
    public static final String DATABASE_TAG = "Database";
    public static final String NAME_TAG = "name";
    public static final String DATABASES_TAG = "Databases";

    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private Node databasesElement;

    public CatalogImpl() {

        this.loadXML();
        databasesElement = doc.getFirstChild();
    }

    /**
     * This function loads xml into doc
     */
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

    /**
     * @param dbName - name of the db node you want to insert
     *               TODO: validate name for duplicates
     */
    public void saveDatabase(String dbName) {

        Element databaseElement = doc.createElement(DATABASE_TAG);
        databaseElement.setAttribute(NAME_TAG, dbName);
        databasesElement.appendChild(databaseElement);

        submitChangesToFile();
    }

    /**
     * @param dbName
     * TODO: not found
     */
    public void dropDatabase(String dbName) {

        Element dbElem = (Element)this.databasesElement;
        NodeList nodeList = doc.getElementsByTagName(DATABASE_TAG);

        for(int i=0; i<nodeList.getLength(); i++){
            Element currentElement = (Element)nodeList.item(i);
            if(currentElement.getAttribute(NAME_TAG).equals(dbName)){
                currentElement.getParentNode().removeChild(currentElement);
            }
        }

        submitChangesToFile();
    }

    public void saveTable(String dbName, String tableName) {

    }

    public void dropTable(String dbName, String tableName) {

    }

    private void submitChangesToFile() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(FILE_NAME);

        try {
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
