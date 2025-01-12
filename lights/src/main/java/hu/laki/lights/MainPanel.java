package hu.laki.lights;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.Timer;

import hu.laki.lights.model.MassPoint;
import hu.laki.lights.model.Universe;
import hu.laki.lights.view.View;

public class MainPanel extends JComponent {

    private Timer uiTimer;
    private final View<Universe> view;
    private int strength = 5;

    public MainPanel(LightsConfig config, View<Universe> view) {
        this.view = view;
        uiTimer = new Timer(Math.max(1000 / config.getFps(), 2), e -> repaint());

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (componentEvent.getID() == ComponentEvent.COMPONENT_RESIZED) {
                    view.getModel().setValidSpace(componentEvent.getComponent().getBounds());
                }
            }
        });
        addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 27) {
                    System.exit(0);
                }
                if (e.getKeyChar() >= '1' && e.getKeyChar() <= '9') {
                    strength = e.getKeyChar() - '1' + 1;
                }   
            }

        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getModel().addMassPoint(MassPoint.builder()
                .location(new Point2D.Double(e.getX(), e.getY()))
                .mass(strength*5)
                .build()); 
                System.out.println(view.getModel().getMassPoints().size());
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                view.getModel().cursorLocationChanged(e.getPoint());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                view.getModel().cursorLocationChanged(new Point(getSize().width/2, 10000));
            };
        });

        uiTimer.start();

        setBackground(new Color(0, 0, 50));
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);

    }

    void setAntiAliasing(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    }

    long prevTst = System.currentTimeMillis();
    @Override
    protected void paintComponent(Graphics g) {
        //System.out.print((System.currentTimeMillis()-prevTst)+",");
        prevTst = System.currentTimeMillis();
        super.paintComponent(g);

        if (g instanceof Graphics2D) {
            //setAntiAliasing((Graphics2D) g);
        }

        g.setColor(Color.WHITE);// new Color((int)(shade*0.7), (int)(shade*0.9), shade));
        view.render(g);
    }

    public Dimension getWindowDimensions() {
        return getSize();
    }

}
