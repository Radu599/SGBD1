package Main;

import Service.Catalog;
import Service.CatalogImpl;

public class Main {

    public static void main(String[] args) {

        Catalog catalog = new CatalogImpl();
        //catalog.saveDatabase("Radu");
        //catalog.dropDatabase("anjnaja") ;
        catalog.dropTable("Radu", "mytable");
        //catalog.saveTable("Radu", "mytable", "table.txt", "12");

    }
}
