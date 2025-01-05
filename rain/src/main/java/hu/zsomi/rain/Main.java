package hu.zsomi.rain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import hu.zsomi.rain.model.Particles;

public class Main {

	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	public static double ACC = 0;
	public static double WIND_SPEED = 0;

    public static void main(String[] args) {
		if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
			Toolkit.getDefaultToolkit().sync();
		}
        List<String> lst = new ArrayList<>();
        lst.add("wonderful");
        lst.add("nice");
        System.out.println("Hello "+lst+" world!");

        JFrame window = new JFrame("Rain shapes");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Particles particleModel = new Particles();
		final JComponent rainArea = new MainPanel(particleModel);
		rainArea.setPreferredSize(new Dimension(SCREEN_WIDTH, 600));

		window.getContentPane().add(rainArea, BorderLayout.CENTER);
		rainArea.setFocusable(true);
		window.getContentPane().setBackground(Color.BLACK);
		window.pack();
		window.setVisible(true);
    }
}