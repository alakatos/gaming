package hu.zsomi.rain;

import java.awt.Dimension;
import java.awt.Point;

public interface RenderingContext {
    Point getMouseLocation();

    Dimension getWindowDimensions();

    RainConfig getConfig();
}
