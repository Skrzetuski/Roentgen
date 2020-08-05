package pl.roentgen.util.builder;

import javafx.scene.paint.Paint;
import pl.roentgen.util.model.Point;

public final class PointBuilder {

    private int id;

    private int x;

    private int y;

    private boolean visible;

    private Paint color;

    private PointBuilder() { }

    public static PointBuilder builder() { return new PointBuilder();
    }

    public PointBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PointBuilder setX(double x) {
        this.x = (int) x;
        return this;
    }

    public PointBuilder setY(double y) {
        this.y = (int) y;
        return this;
    }


    public PointBuilder setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public PointBuilder setColor(Paint color) {
        this.color = color;
        return this;
    }

    public Point build() {
        Point point = new Point(x, y);
        point.setId(id);
        point.setVisible(visible);
        point.setFill(color);
        return point;
    }
}
