
import java.io.File;

public class CatalogImpl implements Catalog {

    private static final String FILE_NAME = "Catalog.xml";

    public CatalogImpl(){

        File file = new File(FILE_NAME);

    }

    public void saveDatabase(String dbName) {

    }

    public void dropDatabase(String dbName) {

    }

    public void saveTable(String dbName, String tableName) {

    }

    public void dropTable(String dbName, String tableName) {

    }
}
