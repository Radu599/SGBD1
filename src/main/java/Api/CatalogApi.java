package Api;

import Service.Catalog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog")
public class CatalogApi {

    private final Logger logger = LogManager.getLogger(CatalogApi.class);

    @Autowired
    private Catalog catalog;

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test() {

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/saveDb/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveDatabase(@PathVariable("name") String name) {

        logger.info("LOG START - saveDatabase");

        catalog.saveDatabase(name);

        logger.info("LOG FINISH - saveDatabase");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/dropDb/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dropDatabase(@PathVariable("name") String name) {

        logger.info("LOG START - dropDatabase");

        catalog.dropDatabase(name);

        logger.info("LOG FINISH - dropDatabase");
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler()
    @ResponseBody
    public ResponseEntity handleException() {

        return new ResponseEntity(HttpStatus.OK);
    }
}
