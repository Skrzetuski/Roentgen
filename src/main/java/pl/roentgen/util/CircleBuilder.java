package pl.roentgen.util;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public final class CircleBuilder {

    private String id;

    private int x;

    private int y;

    private double radius;

    private boolean visible;

    private int translateX;

    private int translateY;

    private Paint color;

    private CircleBuilder() { }

    public static CircleBuilder builder() { return new CircleBuilder();
    }

    public CircleBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CircleBuilder setX(double x) {
        this.x = (int) x;
        return this;
    }

    public CircleBuilder setY(double y) {
        this.y = (int) y;
        return this;
    }

    public CircleBuilder setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public CircleBuilder setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public CircleBuilder setTranslateX(double translateX) {
        this.translateX = (int) translateX;
        return this;
    }

    public CircleBuilder setTranslateY(double translateY) {
        this.translateY = (int) translateY;
        return this;
    }

    public CircleBuilder setColor(Paint color) {
        this.color = color;
        return this;
    }

    public Circle build() {
        Circle circle = new Circle();
        circle.setId(id);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        circle.setVisible(visible);
        circle.setTranslateX(translateX);
        circle.setTranslateY(translateY);
        circle.setFill(color);
        return circle;
    }
}
