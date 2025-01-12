package hu.laki.lights.model;

import java.awt.geom.Point2D;

import hu.laki.gaming.geometry.Vector2D;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Star {
    @Setter
    private Point2D location;
    private final int size;

    public Star(Point2D location, int size) {
        this.location = location;
        this.size = size;
    }

    private Vector2D speed = new Vector2D(1, 0);

    public void calculateNextPosition(Universe universe) {
        Vector2D accelVector = new Vector2D(0, 0);

        for (MassPoint massPoint : universe.getMassPoints()) {
            Vector2D starToMassPointVector = new Vector2D(location, massPoint.getLocation());
            double distance = starToMassPointVector.getLength();
            if (distance > massPoint.getZeroGravityRadius()) {
    
                double mass = massPoint.getMass();
                double force = mass;
                switch (massPoint.getAttenuationType()) {
                    case QBIC:
                        force = force / distance;
                    case QUADRATIC:
                        force = force / distance;
                    case LINEAR:
                        force = force / distance;
                }
    
                accelVector = accelVector.add(starToMassPointVector.scale(force));
    
            }
        }
        speed = speed.add(accelVector);
        location.setLocation(location.getX() + speed.getPoint().getX(), location.getY() + speed.getPoint().getY());
    }
}
