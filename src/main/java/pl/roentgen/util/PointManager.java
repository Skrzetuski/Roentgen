package pl.roentgen.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.roentgen.util.model.Point;

import java.util.Comparator;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

public class PointManager extends Observable {

    private ObservableList<Point> points = FXCollections.observableArrayList();

    public static final AtomicInteger idPoint = new AtomicInteger(0);

    public void setPoints(ObservableList<Point> points) {
        this.points = points;
        points.sort(Comparator.comparingInt(Point::getId));
        setChanged();
        notifyObservers();
    }

    public void addPoint(Point point){
        points.add(point);
        setPoints(points);
    }

    public void removePoint(Point point) {
        points.remove(point);
        setPoints(points);
    }

    public void changePointPosition(int idPoint, double x, double y) {
        Point pointToChange = points.stream().filter(point -> sameID(point, idPoint)).findFirst().get();
        pointToChange.setX((int)x);
        pointToChange.setY((int)y);
        setPoints(points);
    }

    public void changePointVisibility(int idPoint, boolean visible) {
        points.stream().filter(point -> sameID(point, idPoint)).findFirst().get().setVisible(visible);
        setPoints(points);
    }

    public ObservableList<Point> getPoints() {
        return this.points;
    }

    public int getId(){
        return idPoint.getAndIncrement();
    }

    private boolean sameID(Point point, int id){
        return point.getId() == id;
    }
}
