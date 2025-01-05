package hu.zsomi.rain.model;

import java.awt.Dimension;
import java.awt.Point;

public interface SpaceGeometryProvider {
    Point getMouseLocation();
    Dimension getWindowDimensions();
}
