package telran.garage;

import java.util.concurrent.BlockingQueue;

public class Worker extends Thread {

	private BlockingQueue<Car> bq;
	private long sleepTime;
	private long nFixedCars;
	private int num;
	private boolean inWork = false;
	
	public Worker(BlockingQueue<Car> bq, int num) {
		this.bq = bq;
		this.num = num;
		sleepTime = 0;
		nFixedCars = 0;
	}
	
	@Override
	public void run() {
		while (true) {
			Car carToFix;
			try {
				long start = System.currentTimeMillis();
				carToFix = bq.take();
				long finish = System.currentTimeMillis();
				sleepTime = sleepTime + (finish - start);
				inWork = true;
				Thread.sleep(carToFix.fixTime);
				inWork = false;
				nFixedCars++;
			} catch (InterruptedException e) {
				do {
					long start = System.currentTimeMillis();
					carToFix = bq.poll();
					long finish = System.currentTimeMillis();
					sleepTime = sleepTime + (finish - start);
					if (carToFix != null) {
						try {
							inWork = true;
							Thread.sleep(carToFix.fixTime);
							inWork = false;
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						nFixedCars++;
					}
				} while (carToFix != null);
				break;
			}
		}
	}

	public long getnFixedCars() {
		return nFixedCars;
	}

	public int getNum() {
		return num;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public boolean isInWork() {
		return inWork;
	}
	
}
