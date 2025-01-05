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
import lombok.Getter;

public class MainPanel extends JComponent implements RenderingContext {

    private Timer uiTimer;
    private final Particles particleModel;
    @Getter
    private Point mouseLocation;
    @Getter
    private final RainConfig config;

    private class CalcThread extends Thread {
        @Override
        public void run() {
            int cnt = 0;
            long tstSum = 0;
            while (true) {
                long tst = System.currentTimeMillis();
                particleModel.calcParticleLocations();
                long cycleDuration = System.currentTimeMillis() - tst;
                tstSum += cycleDuration;
                if (cnt++ % 1000 == 0) {
                    System.out.println(String.format("%d particles calculated in %.2f ms", particleModel.getParticleCount(), tstSum / 1000d));
                    tstSum = 0;
                }
                
                long cycleMillis = Math.max((int)(1000d/config.getCps()), 1);
                if (cycleDuration < cycleMillis) {
                    try {
                        Thread.sleep(cycleMillis - cycleDuration);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public MainPanel(RainConfig config) {
        this.config = config;
        particleModel = new Particles(this);
        uiTimer = new Timer(Math.max(1000/config.getFps(), 2), e -> repaint());

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
                    particleModel.setIntensity(e.getKeyChar() - '0');
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
            }
        });

        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);

        mouseLocation = new Point(getSize().width / 2, 10000);// , getSize().height/2);
        uiTimer.start();
        new CalcThread().start();
    }

    void setAntiAliasing(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // super.paintComponent(g);

        if (g instanceof Graphics2D) {
            // setAntiAliasing((Graphics2D) g);
        }

        g.setColor(Color.WHITE);// new Color((int)(shade*0.7), (int)(shade*0.9), shade));
        particleModel.getParticles().forEach(p -> p.paint(g));
    }


    @Override
    public Dimension getWindowDimensions() {
        return getSize();
    }

}
