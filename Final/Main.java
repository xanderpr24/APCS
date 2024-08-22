import java.io.File;
import java.util.List;

/**
 * Alexander Melby
 * Period 2
 * Final Project
 * Displays data pertaining to heart disease patients
 */
public class Main {
    private static List<Entry> data;
    private static String[] headers;
    public static void main(String[] args) {
        File file = new File("heart.csv");
        data = EntryLoader.loadFromFile(file);
        headers = EntryLoader.getHeaders(file);

        new App(data, headers);
    }




}
