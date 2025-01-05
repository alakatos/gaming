package hu.zsomi.rain;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import hu.zsomi.rain.model.Rain;
import hu.zsomi.rain.model.Raindrop;
import hu.zsomi.rain.model.SpaceGeometryProvider;

public class RainPanel extends JComponent implements SpaceGeometryProvider {

    private Timer uiTimer;
    private final Rain rainModel;
    private int intensity = 8;
	private Point mouseLocation = null;

    public RainPanel(Rain rainModel) {
        this.rainModel = rainModel;
		rainModel.setMouseLocationProvider(this);
        this.uiTimer = new Timer(10, e -> repaint());

        addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                    intensity = e.getKeyChar() - '0';
                }
            }

        });

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseLocation = e.getPoint();
			}});

        setFocusable(true);
        requestFocusInWindow();

        uiTimer.start();
    }

    void setAntiAliasing(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mouseLocation == null) {
         mouseLocation = new Point(getSize().width/2, 10000);//, getSize().height/2);
        }
        
        if (g instanceof Graphics2D) {
            setAntiAliasing((Graphics2D) g);
        }

        for (Raindrop drop : rainModel.getRaindrops()) {
            drop.paint(g);
            drop.calcNextLocation();
        }

        rainModel.removeOldRaindrops();

        for (int idx = 0; idx < intensity; idx++) {
            rainModel.addRaindrop();
        }

    }



	@Override
	public Point getMouseLocation() {
		return mouseLocation;
	}

    @Override
    public Dimension getWindowDimensions() {
        return getSize();
    }

    
}
