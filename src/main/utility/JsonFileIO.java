package utility;

import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    
    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        String line = null;
        String megaLine = "";
        TaskParser p = new TaskParser();

        try {
            // FileReader reads text files in the default encoding
            FileReader fileReader = new FileReader(jsonDataFile);

            // Wrapping FileReader in BufferedReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                megaLine = megaLine + line;
            }

            //Close files
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + jsonDataFile + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + jsonDataFile + "'");
        }

        return p.parse(megaLine);
    }
    
    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        Jsonifier j = new Jsonifier();
        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(jsonDataFile, true);

            JSONArray allTasks = j.taskListToJson(tasks);

            FileWriter fileWriter1 = new FileWriter(jsonDataFile, false);

            // Wrapping FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter1);

            // Write
            bufferedWriter.write(allTasks.toString(4));

            bufferedWriter.close();

        } catch (IOException ex) {
            System.out.println("Error writing to file '" + jsonDataFile + "'");
        }

    }
}
