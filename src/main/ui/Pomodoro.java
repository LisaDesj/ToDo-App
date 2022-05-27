package ui;

import controller.EditTaskController;
import controller.PomodoroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import model.Task;

import java.io.File;
import java.io.IOException;

// Pomodoro task UI
public class Pomodoro extends StackPane {
    private static final String FXML = "resources/fxml/Pomodoro.fxml";
    private File fxmlFile = new File(FXML);
//    private Task task;

//    public EditTask(Task task) {
//        this.task = task;
//        this.load();
//    }

    public Pomodoro() {
        this.load();
    }

    private void load() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile.toURI().toURL());
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            PomodoroController controller = fxmlLoader.<PomodoroController>getController();
            //controller.setTask(task);
            controller.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
