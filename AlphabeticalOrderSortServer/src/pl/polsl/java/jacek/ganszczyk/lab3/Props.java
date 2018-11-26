package pl.polsl.java.jacek.ganszczyk.lab3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class for taking care of aplication propperties, static method to get the values
 *
 * @author Jacek Ganszczyk
 * @version 2.0-online
 */
public class Props {

    /***
     * New static properties field
     */
    private static final Properties properties = new Properties();

    /**
     * method returns given properties
     * @param propName
     * @return
     */
    public static String getProps(String propName) {
        return properties.getProperty(propName);
    }

    /**
     * Method loads at begining all needed properties
     */
    public static void loadProperties() {
        try (FileInputStream in = new FileInputStream("config.properties")) {
            properties.load(in);
            System.out.println("\n.property");
            System.out.println("Server port=" + properties.getProperty("PORT"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method creates properties for first run of server
     */
    public static void setProperties() {
        properties.setProperty("PORT", "8888");
        String filesDirGet = System.getProperty("user.dir");
        properties.setProperty("FILES_DIR", filesDirGet + "\\dataFiles");
        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            properties.store(out, "--Konfiguracja--");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        /* writing properties into xml file*/
        try (FileOutputStream out = new FileOutputStream("app.xml")) {
            properties.storeToXML(out, "--Konfiguracja--");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
