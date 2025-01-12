package hu.laki.lights.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Universe {
    private final List<Star> stars = new LinkedList<>();
    @Setter
    private Rectangle validSpace;

    private int maxNumberOfStars;
    private final Queue<Star> workerQueue = new LinkedBlockingQueue<>();
    public final static Star deathStar = new Star(null, 0);

    {
        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                Star star = workerQueue.poll();
                if (star == deathStar) {
                    break;
                }
                if (star == null) {
                    Thread.yield();
                } else {
                    star.calculateNextPosition(this);
                }
            }
        });

    }

    public void cursorLocationChanged(Point newLocation) {
        massPoints.get(0).setLocation(newLocation);
    }

    private final List<MassPoint> massPoints = new ArrayList<>();

    public void addStars() {
        if (stars.size() < maxNumberOfStars) {
            for (int i = 0; i < 10; i++) {
                stars.add(new Star(new Point2D.Double(Math.random() * 100, Math.random() * 100), 2));
            }
        }
    }

    public void calcNext(Star star) {
        workerQueue.offer(star);
    }

    public Universe initialMassPoint(MassPoint massPoint) {
        massPoints.clear();
        massPoints.add(massPoint);
        return this;
    }

    public synchronized void addMassPoint(MassPoint massPoint) {
        massPoints.add(massPoint);
    }
}
