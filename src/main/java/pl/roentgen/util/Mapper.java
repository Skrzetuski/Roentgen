package pl.roentgen.util;

import javafx.scene.shape.Circle;

public interface Mapper {

    default Circle toNewCircle(Circle circle) {
        Circle newCircle = new Circle(circle.getRadius());
        newCircle.setId(circle.getId());
        newCircle.setVisible(circle.isVisible());
        newCircle.setCenterX(circle.getCenterX());
        newCircle.setCenterY(circle.getCenterY());
        newCircle.setTranslateX(circle.getTranslateX());
        newCircle.setTranslateY(circle.getTranslateY());
        newCircle.setFill(circle.getFill());
        newCircle.setOnMouseClicked(circle.getOnMouseClicked());
        newCircle.setOnMouseDragged(circle.getOnMouseDragged());
        return newCircle;
    }
}
