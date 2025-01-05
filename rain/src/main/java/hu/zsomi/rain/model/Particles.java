package hu.zsomi.rain.model;

import java.util.ArrayList;
import java.util.List;

public class Particles {
    private List<Particle> particles = new ArrayList<>();
    private SpaceGeometryProvider mouseLocationProvider;
    private int intensity = 8;

    public void setMouseLocationProvider(SpaceGeometryProvider mouseLocationProvider) {
        this.mouseLocationProvider = mouseLocationProvider;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    private void addParticle() {
        if (particles.size() < 50000) {
          int randomLocationXCoord = (int)(Math.random()*(mouseLocationProvider.getWindowDimensions().width));
          particles.add(new Particle(randomLocationXCoord, mouseLocationProvider));
        }
    }

    private void cleanup() {
        List<Particle> particlesToRemove = new ArrayList<>();
        for(Particle p: particles) {
            if (p.isOutOfScreen()) {
                particlesToRemove.add(p);
            }
        }
        particles.removeAll(particlesToRemove);
    
    }

    public void calcParticleLocations() {
        particles.parallelStream().spliterator().forEachRemaining(p -> p.calcNextLocation());
        //particles.forEach(p -> p.calcNextLocation());
        cleanup();
        addNewParticles();
    }

    private void addNewParticles() {
        for (int idx = 0; idx < intensity; idx++) {
            addParticle();
        }
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}
