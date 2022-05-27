package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utility.Logger;

import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Pomodoro UI
public class PomodoroController {
    @FXML
    private JFXButton resetButton;

    // EFFECTS: loads the UI
    public void load() {
        Logger.log("PomodoroController", "Load UI");
        //TODO
    }

}
