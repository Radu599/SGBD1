package Service;

import Model.Column;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
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
import Constants.XMLConstants;

@org.springframework.stereotype.Service
@Primary
@Component
public class CatalogImpl implements Catalog {

    public static final String FILE_NAME = "Catalog.xml";
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private Node databasesElement;

    /**
     *
     */
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

        Element tablesElement = doc.createElement(XMLConstants.TABLES_TAG);
        Element databaseElement = doc.createElement(XMLConstants.DATABASE_TAG);
        databaseElement.appendChild(tablesElement);
        databaseElement.setAttribute(XMLConstants.NAME_TAG, dbName);
        databasesElement.appendChild(databaseElement);

        submitChangesToFile();
    }

    /**
     * @param dbName
     * TODO: not found
     */
    public void dropDatabase(String dbName) {

        NodeList nodeList = doc.getElementsByTagName(XMLConstants.DATABASE_TAG);

        for(int i=0; i<nodeList.getLength(); i++){
            Element currentElement = (Element)nodeList.item(i);
            if(currentElement.getAttribute(XMLConstants.NAME_TAG).equals(dbName)){
                currentElement.getParentNode().removeChild(currentElement);
            }
        }

        submitChangesToFile();
    }

    /**
     * @param dbName
     * @param tableName
     * @param fileName
     * @param rowLength
     */
    public void saveTable(String dbName, String tableName, String fileName, String rowLength, Column... columns) {

        Element structureElement = doc.createElement(XMLConstants.STRUCTURE_TAG);
        Element primaryKeyElement = doc.createElement(XMLConstants.PRIMARY_KEY_TAG);
        Element uniqueKeysElement = doc.createElement(XMLConstants.UNIQUE_KEYS_TAG);
        Element indexFilesElement = doc.createElement(XMLConstants.INDEX_FILES_TAG);

        for (Column column:
             columns) {
            Element columnElement = doc.createElement(XMLConstants.ATTRIBUTE_TAG);
            columnElement.setAttribute(XMLConstants.ATTRIBUTE_NAME_TAG, column.getAttributeName());
            columnElement.setAttribute(XMLConstants.TYPE_TAG, column.getType());
            columnElement.setAttribute(XMLConstants.LENGTH_TAG, String.valueOf(column.getLength()));
            columnElement.setAttribute(XMLConstants.IS_NULL_TAG, String.valueOf(column.isNull()));

            structureElement.appendChild(columnElement);

            if(column.isPrimaryKey()){
                Element pkAttributeElement = doc.createElement(XMLConstants.PRIMARY_KEY_ATTRIBUTE_TAG);
                pkAttributeElement.setTextContent(column.getAttributeName());

                primaryKeyElement.appendChild(pkAttributeElement);
            }

            if(column.isUniqueKey()){
                Element uniqueAttributeElement = doc.createElement(XMLConstants.UNIQUE_ATTRIBUTE_TAG);
                uniqueAttributeElement.setTextContent(column.getAttributeName());

                uniqueKeysElement.appendChild(uniqueAttributeElement);
            }

            if(column.isHasIndex()){ // TODO: can indexes be composite? if so in this if we have to add an other if so we don't duplicate <IndexFile> but group them under same tag
                Element indexFileElement = doc.createElement(XMLConstants.INDEX_FILES_TAG);
                indexFileElement.setAttribute(XMLConstants.INDEX_NAME_TAG, column.getIndexName());
                indexFileElement.setAttribute(XMLConstants.KEY_LENGTH_TAG, String.valueOf(column.getKeyLength()));
                indexFileElement.setAttribute(XMLConstants.IS_UNIQUE_TAG, String.valueOf(column.isUnique()));
                indexFileElement.setAttribute(XMLConstants.INDEX_TYPE_TAG, column.getIndexType());

                indexFilesElement.appendChild(indexFileElement);

                Element indexAttributes = doc.createElement(XMLConstants.INDEX_ATTRIBUTES_TAG);
                indexFileElement.appendChild(indexAttributes);

                Element iAttributeElement = doc.createElement(XMLConstants.I_ATTRIBUTE_TAG);
                iAttributeElement.setTextContent(column.getIndexName());

                indexAttributes.appendChild(iAttributeElement);
            }
        }

        Element tableElement = doc.createElement(XMLConstants.TABLE_TAG);
        tableElement.setAttribute(XMLConstants.TABLE_NAME_TAG, tableName);
        tableElement.setAttribute(XMLConstants.FILE_NAME_TAG, fileName);
        tableElement.setAttribute(XMLConstants.ROW_LENGTH_TAG, rowLength);

        tableElement.appendChild(structureElement);
        tableElement.appendChild(primaryKeyElement);
        tableElement.appendChild(uniqueKeysElement);
        tableElement.appendChild(indexFilesElement);

        NodeList nodeList = doc.getElementsByTagName(XMLConstants.TABLES_TAG);

        for(int i=0; i<nodeList.getLength(); i++){
            Element currentTablesElement = (Element)nodeList.item(i);
            Element database = (Element)currentTablesElement.getParentNode();

            if(database.getAttribute(XMLConstants.NAME_TAG).equals(dbName)){
                currentTablesElement.appendChild(tableElement);
            }
        }

        submitChangesToFile();
    }

    public void dropTable(String dbName, String tableName) {

        NodeList tableList = doc.getElementsByTagName(XMLConstants.TABLE_TAG);

        for(int i=0; i<tableList.getLength(); i++){
            Element currentTable = (Element)tableList.item(i);
            Element currentDb = (Element) currentTable.getParentNode().getParentNode();

            if(currentTable.getAttribute(XMLConstants.TABLE_NAME_TAG).equals(tableName)
            && currentDb.getAttribute(XMLConstants.NAME_TAG).equals(dbName)){
                currentTable.getParentNode().removeChild(currentTable);
            }
        }

        submitChangesToFile();
    }

    /**
     * Applies changes to file
     */
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
