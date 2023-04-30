package telran.garage;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Garage extends Thread {

	private BlockingQueue<Car> bq;
	private Random random;
	private long timeToWork;
	private long ignoredCars = 0;

	public Garage(BlockingQueue<Car> bq, long timeToWork) {
		this.bq = bq;
		this.random = new Random();
		this.timeToWork = timeToWork;
	}

	@Override
	public void run() {
		for (int i = 0; i < timeToWork; i++) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int res = random.nextInt(100);
			if (res >= 85) {
				Car newCar = new Car();
				if (!bq.offer(newCar)) {
					ignoredCars++;
				}
			}
		}
	}

	public long getIgnoredCars() {
		return ignoredCars;
	}
}
