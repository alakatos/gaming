package hu.laki.lights;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

import hu.laki.lights.model.MassPoint;
import hu.laki.lights.model.Universe;
import hu.laki.lights.model.MassPoint.AttenuationType;
import hu.laki.lights.view.FlatView;
import picocli.CommandLine;

public class LightsLauncher {
    public static void main(String[] args) {
        new CommandLine(new LightsConfig(config -> createWindow(config)))
                .execute(args);
    }

    private static void createWindow(LightsConfig config) {
        if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
            Toolkit.getDefaultToolkit().sync();
        }

        JFrame window = new JFrame("Particles");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension initialWindowSize = new Dimension(800, 600);
        final JComponent universeDisplay = new MainPanel(config,
                new FlatView(
                        Universe.builder()
                        .validSpace(new Rectangle(0, 0, initialWindowSize.width, initialWindowSize.height))
                        .maxNumberOfStars(config.getMaxNumberOfStars())
                        .build().initialMassPoint(
                            MassPoint.builder().location(new Point2D.Double(300, 300)).mass(config.getG()).zeroGravityRadius(config.getZeroGravityRadius()).build()
                        )));

        universeDisplay.setPreferredSize(initialWindowSize);
        window.getContentPane().add(universeDisplay, BorderLayout.CENTER);
        window.getContentPane().setBackground(new Color(0, 0, 20));
        universeDisplay.setFocusable(true);
        window.pack();
        window.setVisible(true);
    }

}
