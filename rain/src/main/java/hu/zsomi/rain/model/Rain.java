package hu.zsomi.rain.model;

import java.util.ArrayList;
import java.util.List;

public class Rain {
    private List<Raindrop> raindrops = new ArrayList<>();
    private SpaceGeometryProvider mouseLocationProvider;

    public void setMouseLocationProvider(SpaceGeometryProvider mouseLocationProvider) {
        this.mouseLocationProvider = mouseLocationProvider;
    }

    public List<Raindrop> getRaindrops() {
        return raindrops;
    }

    public void addRaindrop() {
        int randomLocationXCoord = (int)(Math.random()*(mouseLocationProvider.getWindowDimensions().width));
        raindrops.add(new Raindrop(randomLocationXCoord, mouseLocationProvider));
    }

    public void removeOldRaindrops() {
        List<Raindrop> raindropsToRemove = new ArrayList<>();
        for(Raindrop drop: raindrops) {
            if (drop.isOutOfScreen()) {
                raindropsToRemove.add(drop);
            }
        }
        raindrops.removeAll(raindropsToRemove);
    }
    
}
