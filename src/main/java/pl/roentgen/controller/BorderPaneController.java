package pl.roentgen.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import pl.roentgen.util.*;
import pl.roentgen.util.builder.CircleBuilder;
import pl.roentgen.util.builder.PointBuilder;
import pl.roentgen.util.model.Point;

import java.net.URL;
import java.util.*;

public class BorderPaneController implements Initializable, Mapper, ColorGenerator, Observer {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView imageView;

    private final Image image = new Image("/img/x-ray.png");

    private final List<Circle> rewriteList = new ArrayList<>();

    private PointManager observable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        double scaleHeight = (image.getHeight() / borderPane.getPrefHeight()) + 0.2;
        double scaleWidth = (image.getWidth() / borderPane.getPrefWidth()) + 0.2;

        imageView.setFitHeight(image.getHeight() / scaleHeight);
        imageView.setFitWidth(image.getWidth() / scaleWidth);
        imageView.setImage(image);

        imageView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                Point point = PointBuilder.builder()
                        .setId(observable.getId())
                        .setX(x)
                        .setY(y)
                        .setVisible(true)
                        .setColor(generateRandomColor()).build();

                observable.addPoint(point);
            }
        });
    }

    private List<Circle> createCirclesFromPoints(ObservableList<Point> points) {
        List<Circle> circles = new LinkedList<>();
        for (Point point : points) {
            Circle circle = CircleBuilder.builder().setId(String.valueOf(point.getId()))
                    .setRadius(5)
                    .setX(point.getX())
                    .setTranslateX(imageView.getLayoutX())
                    .setY(point.getY())
                    .setTranslateY(imageView.getLayoutY())
                    .setVisible(point.isVisible())
                    .setColor(point.getFill()).build();

            circle.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    observable.removePoint(point);
                }
            });

            circle.setOnMouseDragged(eventDragged -> {
                if (eventDragged.getButton().equals(MouseButton.PRIMARY)) {
                    circle.setOnMouseReleased(event -> observable.changePointPosition(point.getId(),
                                                                                      circle.getCenterX(),
                                                                                      circle.getCenterY()));
                    circle.setCenterX(eventDragged.getX());
                    circle.setCenterY(eventDragged.getY());
                }
            });

            circles.add(circle);
        }
        return circles;
    }

    @Override
    public void update(Observable o, Object arg) {
        borderPane.getChildren().removeAll(rewriteList);
        rewriteList.clear();
        rewriteList.addAll(createCirclesFromPoints(observable.getPoints()));
        borderPane.getChildren().addAll(rewriteList);
    }

    public void setObservable(PointManager observable) {
        this.observable = observable;
    }
}
