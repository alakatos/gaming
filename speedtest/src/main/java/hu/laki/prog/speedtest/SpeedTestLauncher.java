package hu.laki.prog.speedtest;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SpeedTestLauncher {

	Timer timer = new Timer();
	private List<SpeedTestThread> threads = new ArrayList<SpeedTestThread>();
	boolean stop;

	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			int i=1;
			for (SpeedTestThread speedTestThread : threads) {
				System.out.println(i+" "+speedTestThread.getAndResetExecutionCount());
				i++;
			}
			if (!stop) {
				timer.schedule(new MyTimerTask(), 1000);
			}
		}
		
	}
	void startThreads(String[] args) throws InterruptedException {
		int maxNumberOfIterations = Integer.parseInt(args[0]);
		int numberOfEdges = Integer.parseInt(args[1]);
		int numberOfThreads = Integer.parseInt(args[2]);
		
		for (int i = 0; i < numberOfThreads; i++) {
			threads.add(new SpeedTestThread(maxNumberOfIterations, numberOfEdges));
		}

		for (SpeedTestThread speedTestThread : threads) {
			speedTestThread.start();
		}
		
		timer.schedule(new MyTimerTask(), 1000);
		
		int i=1;
		for (SpeedTestThread stThread: threads) {
			stThread.join();
			System.out.println(i+" "+stThread.getAndResetExecutionCount());
			i++;
		}
		
		timer.cancel();
		stop=true;
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		if (args.length < 3) {
			System.out.println("usage: java " + SpeedTestLauncher.class.getName() + " <maxNumberOfIterations> <numberOfEdges> <numberOfThreads>");
			System.exit(1);
		}

		new SpeedTestLauncher().startThreads(args);
	}
}
