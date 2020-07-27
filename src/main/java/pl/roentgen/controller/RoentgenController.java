package pl.roentgen.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RoentgenController implements Initializable {

    @FXML
    private TilePane tilePane;

    @FXML
    private ListView<Circle> listView;

    @FXML
    private BorderPaneController borderPane1Controller;

    @FXML
    private BorderPaneController borderPane2Controller;

    @FXML
    private BorderPaneController borderPane3Controller;

    @FXML
    private BorderPaneController borderPane4Controller;

    private static List<BorderPaneController> borderPaneControllerList;

    private final ObservableList<Circle> circles = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        borderPaneControllerList = Arrays.asList(
                borderPane1Controller,
                borderPane2Controller,
                borderPane3Controller,
                borderPane4Controller);

        tilePane.setPrefRows(2);
        tilePane.setPrefColumns(2);

        listView.setItems(circles);
        listView.setCellFactory(pointsListView -> new PointListViewController(circles));

        for (BorderPaneController bpController : borderPaneControllerList) {
            bpController.setCircles(circles);
        }

        tilePane.setOnMouseClicked((event -> updateFrames()));
    }

    public static void updateFrames() {
        for (BorderPaneController bpController : borderPaneControllerList) {
            Platform.runLater(bpController::updatePoints);
        }
    }
}
