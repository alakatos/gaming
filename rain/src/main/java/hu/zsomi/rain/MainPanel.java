package hu.zsomi.rain;

import java.awt.Color;
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

import hu.zsomi.rain.model.Particles;
import hu.zsomi.rain.model.SpaceGeometryProvider;

public class MainPanel extends JComponent implements SpaceGeometryProvider {

    private Timer uiTimer;
    private Timer calcTimer;
    private final Particles particleModel;
	private Point mouseLocation;

    public MainPanel(Particles rainModel) {
        this.particleModel = rainModel;
		rainModel.setMouseLocationProvider(this);
        uiTimer = new Timer(5, e -> repaint());
        calcTimer = new Timer(5, e -> rainModel.calcParticleLocations());

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
                    rainModel.setIntensity(e.getKeyChar() - '0');
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
        setVisible(true);
        
        calcTimer.start();
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
            //setAntiAliasing((Graphics2D) g);
        }

		g.setColor(Color.WHITE);//new Color((int)(shade*0.7), (int)(shade*0.9), shade));
        particleModel.getParticles().forEach(p->p.paint(g));
    }



	@Override
	public Point getMouseLocation() {
		return mouseLocation == null ? new Point(100,200) : mouseLocation;
	}

    @Override
    public Dimension getWindowDimensions() {
        return getSize();
    }

    
}
