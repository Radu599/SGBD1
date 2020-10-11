
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class CatalogImpl implements Catalog {

    private static final String FILE_NAME = "Catalog.xml";
    public static final String DATABASE_TAG = "database";
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;


    public CatalogImpl() {

        this.dbFactory = DocumentBuilderFactory.newInstance();
        try {
            this.dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        this.doc = dBuilder.newDocument();
    }

    public void loadXML(String file) {

    }

    public void saveDatabase(String dbName) {

        Element databaseElement = doc.createElement(DATABASE_TAG);
        doc.appendChild(databaseElement);

        //Attibute genderAttribute = document.createAttribute("gender");
       // genderAttribute.setValue("F");
    }

    public void dropDatabase(String dbName) {

    }

    public void saveTable(String dbName, String tableName) {

    }

    public void dropTable(String dbName, String tableName) {

    }
}
