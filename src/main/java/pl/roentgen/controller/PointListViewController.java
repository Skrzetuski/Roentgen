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
import java.util.function.Function;

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

    private final Function<NumberField, Circle> fetchCircleById = this::repositoryId;

    private final ObservableList<Circle> circles;

    private FXMLLoader fxmlLoader;

    private int cellId;

    PointListViewController(ObservableList<Circle> circles) {
        this.circles = circles;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        numberFieldX.setOnAction(event -> {
                fetchCircleById.apply(numberFieldX).setCenterX(numberFieldX.getValue());
                RoentgenController.updateFrames();
        });

        numberFieldY.setOnAction(event -> {
                fetchCircleById.apply(numberFieldY).setCenterY(numberFieldY.getValue());
                RoentgenController.updateFrames();

        });

        checkBox.setOnMouseClicked(event -> {
            circles.stream().filter(circle -> circle.getId().equals(String.valueOf(cellId))).findFirst().get()
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

            cellId = Integer.parseInt(circle.getId());

            labelPoint.setText(LABEL_TEXT);
            labelPoint.setTextFill(circle.getFill());

            numberFieldX.setText(String.valueOf((int) circle.getCenterX()));
            numberFieldY.setText(String.valueOf((int) circle.getCenterY()));

            checkBox.setSelected(circle.isVisible());

            setText(null);
            setGraphic(gridPane);
        }
    }

    /***
     *  Access only by fetchCircleById field.
     *  Get circle from list circles by circle id from value at field.
     * @param field any NumberField
     *
     * @return circle from list or new instance Circle
     * if it wouldn't find any circle that passed by ID.
     */
    private Circle repositoryId(NumberField field) {
        if (!field.getText().isEmpty()) {
            return circles.stream()
                    .filter(circle -> circle.getId().equals(String.valueOf(cellId)))
                    .findFirst().get();
        }
        return new Circle(0, 0, 5);
    }
}
