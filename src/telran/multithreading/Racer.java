package telran.multithreading;

import java.util.Random;

public class Racer extends Thread {

	final int MIN_SLEEP = 2;
	final int MAX_SLEEP = 5;
	private int frames;
	private int num;
	static Integer fin = -1;

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

		if (fin == -1) {
			System.out.println("Racer [" + num + "] won a race");
			fin = num;
		} else {
			System.out.println("Racer [" + num + "] finished a race");
		}

	}

}
