package hu.laki.prog.speedtest;

import hu.laki.gaming.geometry.Polygon2D;

public class SpeedTestThread extends Thread {
	
	int executionCount;
	
	int maxIterCount;
	Polygon2D polygon;
	

	public SpeedTestThread(int maxIterCount, int numberOfEdges) {
		this.maxIterCount = maxIterCount;
		polygon =  Polygon2D.createRegularPolygon(Math.random()*50+10, numberOfEdges).rotate(Math.random()*360);
	}

	@Override
	public void run() {
		executionCount = 0;
		for (int i = 0; i < maxIterCount; i++) {
			polygon.scale(Math.random()*10+1).rotate((0.5-Math.random())*180).cutRadiallyToNEqualAreaTriangles();
			executionCount++;
		}
	}
	
	public int getAndResetExecutionCount() {
		int count = executionCount;
		executionCount = 0;
		return count;
	}

	public int getExecutionCount() {
		return executionCount;
	}
	
}
