package hu.zsomi.rain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.Callable;

import javax.swing.JComponent;
import javax.swing.JFrame;

import hu.zsomi.rain.model.Particles;
import lombok.Getter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="Rain")
@Getter
public class RainConfig implements Callable<Integer>{

	@Option(names = {"-np", "--particles"}, description = "Max number of particles (${DEFAULT-VALUE})", defaultValue = "10000", required = false)
    private int maxNumberOfParticles;

	@Option(names = {"-zgr", "--zero-gravity-radius"}, description = "Zero G area radius around the mouse cursor (${DEFAULT-VALUE})", defaultValue = "100", required = false)
    private int zeroGravityRadius;

	@Option(names = {"-fps", "--frame-per-second"}, description = "Frame per seconds to render (${DEFAULT-VALUE})", defaultValue = "120", required = false)
    private int fps;

	@Option(names = {"-cps", "--cycle-per-second"}, description = "Cycle per seconds to calculate particle positions (${DEFAULT-VALUE})", defaultValue = "100", required = false)
    private int cps;

	@Option(names = {"-g", "--gravity"}, description = "Gravity force (${DEFAULT-VALUE})", defaultValue = "10", required = false)
	private int g;

	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	public static double ACC = 0;

	public Integer call() throws Exception {
		if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
			Toolkit.getDefaultToolkit().sync();
		}

		JFrame window = new JFrame("Particles");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JComponent particleArea = new MainPanel(this);
		particleArea.setPreferredSize(new Dimension(SCREEN_WIDTH, 600));

		window.getContentPane().add(particleArea, BorderLayout.CENTER);
		particleArea.setFocusable(true);
		window.getContentPane().setBackground(Color.BLACK);
		window.pack();
		window.setVisible(true);
		return 0;
	}
}