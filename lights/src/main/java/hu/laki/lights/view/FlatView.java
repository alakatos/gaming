package hu.laki.lights.view;

import java.awt.Graphics;

import hu.laki.lights.model.Universe;

public class FlatView extends View<Universe> {

    public FlatView(Universe model) {
        super(model);
    }

    @Override
    public void render(Graphics g) {
        getModel().getStars().forEach(s -> { 
            g.drawRect((int) s.getLocation().getX(), (int) s.getLocation().getY(), s.getSize(), s.getSize());
            getModel().calcNext(s);
        });
        getModel().getMassPoints().forEach(mp -> { 
            g.drawOval((int) mp.getLocation().getX(), (int) mp.getLocation().getY(), 5, 5);
        });
        getModel().addStars();
    }
}
