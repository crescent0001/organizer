package scene.event.UI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import scene.event.EventController;
import scene.event.entity.Event;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Liu Woon Kit on 29/6/2017.
 */
public class EventListController implements Initializable {
    @FXML
    Label clockDisplay;

    @FXML
    HBox newEventListBox;

    @FXML
    HBox eventListBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventController.setEventListController(this);
        displayEvents();
        startClock();
    }

    public void displayEvents() {
        clearDisplay();
        for(Event event : EventController.getEvents()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EventObject.fxml"));
            fxmlLoader.setController(new EventObjectController(event));
            try {
                if(event.isRegisteredByUser()) {
                    eventListBox.getChildren().add(fxmlLoader.load());
                }
                else {
                    newEventListBox.getChildren().add(fxmlLoader.load());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearDisplay() {
        newEventListBox.getChildren().clear();
        eventListBox.getChildren().clear();
    }

    // Unimportant
    private static Date date;
    private static Timer timer = new Timer();
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    public void startClock() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                date = new Date();
                Platform.runLater(() -> clockDisplay.setText(dateFormat.format(date)));
            }
        },0, 1000);
    }

    @FXML
    public void manageEvents() {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(new FXMLLoader(getClass().getResource("EventAdd.fxml")).load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}