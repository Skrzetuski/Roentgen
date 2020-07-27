package pl.roentgen.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import pl.roentgen.util.CircleBuilder;
import pl.roentgen.util.ColorGenerator;
import pl.roentgen.util.Mapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BorderPaneController implements Initializable, Mapper, ColorGenerator {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView imageView;

    private final Image image = new Image("/img/x-ray.png");

    private final List<Circle> rewriteList = new ArrayList<>();

    private static final AtomicInteger idCircle = new AtomicInteger(-1);

    private ObservableList<Circle> circles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double scaleHeight = (image.getHeight() / borderPane.getPrefHeight()) + 0.2;
        double scaleWidth = (image.getWidth() / borderPane.getPrefWidth()) + 0.2 ;


        imageView.setFitHeight(image.getHeight() / scaleHeight);
        imageView.setFitWidth(image.getWidth() / scaleWidth);
        imageView.setImage(image);
        imageView.setOnMouseClicked(mouseEvent -> {

            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                Circle circle = CircleBuilder.builder().setId(String.valueOf(idCircle.incrementAndGet()))
                        .setRadius(5)
                        .setX(x)
                        .setTranslateX(imageView.getLayoutX())
                        .setY(y)
                        .setTranslateY(imageView.getLayoutY())
                        .setVisible(true)
                        .setColor(generateRandomColor()).build();

                circle.setOnMouseClicked(event -> {
                    if (event.getButton().equals(MouseButton.SECONDARY)) {
                        circles.remove(circle);
                        borderPane.getChildren().remove(circle);
                    }
                });

                circle.setOnMouseDragged(eventDragged -> {
                    if (eventDragged.getButton().equals(MouseButton.PRIMARY)) {

                        Circle ciecleTmp = (Circle) eventDragged.getSource();
                        circle.setVisible(false);
                        ciecleTmp.setOnMouseReleased(eventReleased -> {
                            circle.setVisible(true);
                            circles.remove(circle);
                            circles.add(circle);
                            circles.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getId())));
                        });

                        circle.setCenterX(eventDragged.getX());
                        circle.setCenterY(eventDragged.getY());

                        ciecleTmp.setCenterX(circle.getCenterX());
                        ciecleTmp.setCenterY(circle.getCenterY());
                    }
                });

                circles.add(circle);
                borderPane.getChildren().add(circle);
            }
        });
    }

    public void updatePoints() {
        clearPoints();
        clearCircleList();
        mapList(rewriteList, circles);
        borderPane.getChildren().addAll(rewriteList);
    }

    private void clearPoints() {
        borderPane.getChildren().removeAll(rewriteList);
    }

    private void clearCircleList() {
        rewriteList.clear();
    }

    private void mapList(List<Circle> circleList, ObservableList<Circle> observableList) {
        circleList.addAll(observableList.stream().map(this::toNewCircle).collect(Collectors.toList()));
    }

    public void setCircles(ObservableList<Circle> circles) {
        this.circles = circles;
    }
}
