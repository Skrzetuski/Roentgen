package pl.roentgen.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import pl.roentgen.util.PointManager;
import pl.roentgen.util.field.NumberField;
import pl.roentgen.util.model.Point;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PointListViewController extends ListCell<Point> implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label labelPoint;

    @FXML
    private NumberField numberFieldX;

    @FXML
    private NumberField numberFieldY;

    @FXML
    private CheckBox checkBox;

    private static final String LABEL_TEXT = "Punkt";

    private final PointManager observable;

    private FXMLLoader fxmlLoader;

    private int cellId;

    PointListViewController(PointManager observable) {
        this.observable = observable;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        numberFieldX.setOnAction(event -> onActionNumberField());

        numberFieldY.setOnAction(event -> onActionNumberField());

        checkBox.setOnMouseClicked(event -> onMouseClickedCheckBox());
    }

    private void onActionNumberField() {
        observable.changePointPosition(cellId, numberFieldX.getValue(), numberFieldY.getValue());
    }

    private void onMouseClickedCheckBox(){
        observable.changePointVisibility(cellId, checkBox.isSelected());
    }

    @Override
    protected void updateItem(Point point, boolean empty) {
        super.updateItem(point, empty);

        if (point == null || empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/point-list-cell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            cellId = point.getId();

            labelPoint.setText(LABEL_TEXT);
            labelPoint.setTextFill(point.getFill());

            numberFieldX.setText(String.valueOf(point.getX()));
            numberFieldY.setText(String.valueOf(point.getY()));

            checkBox.setSelected(point.isVisible());

            setText(null);
            setGraphic(gridPane);
        }
    }

}
