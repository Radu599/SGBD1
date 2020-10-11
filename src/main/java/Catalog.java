public interface Catalog {

    void saveDatabase(String dbName);
    void dropDatabase(String dbName);
    void saveTable(String dbName, String tableName);
    void dropTable(String dbName, String tableName);
}
