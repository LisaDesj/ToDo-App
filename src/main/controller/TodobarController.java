package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Task;
import ui.*;
import utility.JsonFileIO;
import utility.Logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUpFxmlFile = new File(todoActionsPopUpFXML);
    
    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;
    
    private Task task;
    private JFXPopup todoOptionsPopUp;
    private JFXPopup todoActionsPopUp;
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodobarOptionsPopUp();
        loadTodobarOptionsPopUpActionListener();
        loadTodobarActionsPopUp();
        loadTodobarActionsPopUpActionListener();
    }

    // EFFECTS: load options pop up (To Do, Up Next, In Progress, Done, Pomodoro!)
    private void loadTodobarOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            todoOptionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // EFFECTS: load actions pop up (Edit, Delete)
    private void loadTodobarActionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsPopUpController());
            todoActionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadTodobarOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                todoOptionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show actions pop up when its icon is clicked
    private void loadTodobarActionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                todoActionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: option pop up controller
    class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();

            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarActionsPopUpController","Editing task");
                    commenceEditTask(descriptionLabel);
                    break;
                case 1:
                    Logger.log("TodobarActionsPopUpController","Deleted task");
                    deleteTask(descriptionLabel);
                    break;
                default:
                    Logger.log("TodobarActionsPopUpController","No functionality has been implemented");
            }
            todoOptionsPopUp.hide();
        }
    }

    // Inner class: action pop up controller
    class TodoActionsPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;


        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();

            switch (selectedIndex) {
                case 0: Logger.log("TodobarOptionsPopUpControl","No functionality has been implemented");
                    break;
                case 1: Logger.log("TodobarOptionsPopUpControl","No functionality has been implemented");
                    break;
                case 2: Logger.log("TodobarOptionsPopUpControl","No functionality has been implemented");
                    break;
                case 3: Logger.log("TodobarOptionsPopUpControl","No functionality has been implemented");
                    break;
//                case 4: Logger.log("TodobarOptionsPopUpControl","Pomodoro!");
                case 4: Logger.log("TodobarOptionsPopUpControl","No functionality has been implemented");
                    //pomodoroView();
                    break;
                default:
                    Logger.log("TodobarOptionsPopUpControl","No functionality has been implemented");
            }
            todoActionsPopUp.hide();
        }
    }

    private void deleteTask(Label descriptionLabel) {
        List<Task> currentTasks = PomoTodoApp.getTasks();
        List<Task> decoyList = new LinkedList<>();
        decoyList.addAll(currentTasks);
        for (Task t : decoyList) {
            if (t.getDescription().equals(descriptionLabel.getText())) {
                PomoTodoApp.getTasks().remove(t);
            }
        }
        JsonFileIO.write(PomoTodoApp.getTasks());
        PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
    }

    private void commenceEditTask(Label descriptionLabel) {
        List<Task> currentTasks = PomoTodoApp.getTasks();
        EditTaskController edit = new EditTaskController();
        for (Task t : currentTasks) {
            if (t.getDescription().equals(descriptionLabel.getText())) {
                PomoTodoApp.setScene(new EditTask(t));
            }
        }
    }

    private void pomodoroView() {
        PomoTodoApp.setScene(new Pomodoro());
    }
}
