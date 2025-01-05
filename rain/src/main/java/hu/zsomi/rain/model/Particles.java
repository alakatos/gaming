package hu.zsomi.rain.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hu.zsomi.rain.RenderingContext;
import hu.zsomi.rain.util.MultiThreadedWorker;

public class Particles {
    private List<Particle> particles = new LinkedList<>();
    private RenderingContext context;
    private int intensity = 8;
    private final MultiThreadedWorker<Particle> multiThreadedWorker = new MultiThreadedWorker<>(10);

    public Particles(RenderingContext context) {
        this.context = context;
    }

    public void setContext(RenderingContext mouseLocationProvider) {
        this.context = mouseLocationProvider;
    }

    public synchronized List<Particle> getParticles() {
        return new ArrayList<>(particles);
    }

    private void addParticle() {
        if (particles.size() < context.getConfig().getMaxNumberOfParticles()) {
            int randomLocationXCoord = (int) (Math.random() * (context.getWindowDimensions().width));
            particles.add(new Particle(randomLocationXCoord, context));
        }
    }

    private void cleanup() {
        List<Particle> particlesToRemove = new ArrayList<>();
        for (Particle p : particles) {
            if (p.isOutOfScreen()) {
                particlesToRemove.add(p);
            }
        }
        particles.removeAll(particlesToRemove);

    }

    public void calcParticleLocations() {
        //getParticles().parallelStream().spliterator().forEachRemaining(p -> p.calcNextLocation());
        //getParticles().forEach(p -> p.calcNextLocation());
        
         try {
            multiThreadedWorker.work(getParticles(), p -> p.calcNextLocation());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        

          
        synchronized(this) {
            cleanup();
            addNewParticles();
        }
    }

    private void addNewParticles() {
        for (int idx = 0; idx < intensity; idx++) {
            addParticle();
        }
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getParticleCount() {
        return particles.size();
    }
}
