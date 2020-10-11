package Main;

import Service.Catalog;
import Service.CatalogImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

/*    public static void main(String[] args) {

        Catalog catalog = new CatalogImpl();
        //catalog.saveDatabase("Radu");
        //catalog.dropDatabase("anjnaja") ;
        catalog.dropTable("Radu", "mytable");
        //catalog.saveTable("Radu", "mytable", "table.txt", "12");
    }*/
}
