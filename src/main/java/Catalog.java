public interface Catalog {

    void loadXML(String fileName);
    void saveDatabase(String dbName);
    void dropDatabase(String dbName);
    void saveTable(String dbName, String tableName);
    void dropTable(String dbName, String tableName);
}
