package pl.roentgen.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import pl.roentgen.util.field.NumberField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PointListViewController extends ListCell<Circle> implements Initializable {

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

    private final ObservableList<Circle> circles;

    private FXMLLoader fxmlLoader;

    private int id;

    PointListViewController(ObservableList<Circle> circles) {
        this.circles = circles;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberFieldX.setOnAction(event -> {
            if (!numberFieldX.getText().isEmpty()) {
                circles.stream().filter(circle -> circle.getId().equals(String.valueOf(id))).findFirst().get()
                        .setCenterX(numberFieldX.getValue());
                RoentgenController.updateFrames();
            }
        });

        numberFieldY.setOnAction(event -> {
            if (!numberFieldY.getText().isEmpty()) {
                circles.stream().filter(circle -> circle.getId().equals(String.valueOf(id))).findFirst().get()
                        .setCenterY(numberFieldY.getValue());
                RoentgenController.updateFrames();
            }
        });

        checkBox.setOnMouseClicked(event -> {
            circles.stream().filter(circle -> circle.getId().equals(String.valueOf(id))).findFirst().get()
                    .setVisible(checkBox.isSelected());
            RoentgenController.updateFrames();
        });
    }

    @Override
    protected void updateItem(Circle circle, boolean empty) {
        super.updateItem(circle, empty);

        if (circle == null || empty) {
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

            id = Integer.parseInt(circle.getId());

            labelPoint.setText(LABEL_TEXT);
            labelPoint.setTextFill(circle.getFill());

            numberFieldX.setText(String.valueOf((int) circle.getCenterX()));
            numberFieldY.setText(String.valueOf((int) circle.getCenterY()));

            checkBox.setSelected(circle.isVisible());

            setText(null);
            setGraphic(gridPane);
        }
    }
}
