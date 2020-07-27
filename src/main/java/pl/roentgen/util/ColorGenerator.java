package pl.roentgen.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.concurrent.ThreadLocalRandom;

public interface ColorGenerator {

    default Paint generateRandomColor() {
        return Color.color(
                ThreadLocalRandom.current().nextDouble(0.1, 1),
                ThreadLocalRandom.current().nextDouble(0.1, 1),
                ThreadLocalRandom.current().nextDouble(0.1, 1));
    }
}
