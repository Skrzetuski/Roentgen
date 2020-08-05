package pl.roentgen.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import pl.roentgen.util.PointManager;
import pl.roentgen.util.model.Point;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RoentgenController implements Initializable {

    @FXML
    private TilePane tilePane;

    @FXML
    private ListView<Point> listView;

    @FXML
    private BorderPaneController borderPane1Controller;

    @FXML
    private BorderPaneController borderPane2Controller;

    @FXML
    private BorderPaneController borderPane3Controller;

    @FXML
    private BorderPaneController borderPane4Controller;

    private final static PointManager pointManager = new PointManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<BorderPaneController> borderPaneControllerList = Arrays.asList(
                borderPane1Controller,
                borderPane2Controller,
                borderPane3Controller,
                borderPane4Controller);

        for (BorderPaneController borderPane : borderPaneControllerList) {
            borderPane.setObservable(pointManager);
        }

        pointManager.addObserver(borderPane1Controller);
        pointManager.addObserver(borderPane2Controller);
        pointManager.addObserver(borderPane3Controller);
        pointManager.addObserver(borderPane4Controller);

        tilePane.setPrefRows(2);
        tilePane.setPrefColumns(2);

        listView.setItems(pointManager.getPoints());
        listView.setCellFactory(pointsListView -> new PointListViewController(pointManager));
    }
}
