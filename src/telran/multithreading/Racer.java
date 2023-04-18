package telran.multithreading;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Racer extends Thread {

	final int MIN_SLEEP = 2;
	final int MAX_SLEEP = 5;
	private int frames;
	private int num;
	static AtomicInteger fin = new AtomicInteger(-1);

	public Racer(int frames, int num) {
		this.frames = frames;
		this.num = num;
	}

	@Override
	public void run() {
		for (int i = 0; i < frames; i++) {
			Random random = new Random();
			try {
				sleep(random.nextInt(MIN_SLEEP, MAX_SLEEP + 1));
			} catch (InterruptedException e) {
				// Кто-то разбудил
			}
		}

		fin.compareAndSet(-1, num);

	}

}
