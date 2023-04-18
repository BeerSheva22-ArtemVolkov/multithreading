package telran.multithreading;

import java.time.Instant;
import java.util.Random;

public class Racer extends Thread {

	final int MIN_SLEEP = 2;
	final int MAX_SLEEP = 5;
	
	private int frames;
	private int num;
	private Object mutex = new Object();
	private int position;
	private Instant finishTime;
	private RaceParams rp;

	public Racer(int frames, int num, RaceParams rp) {
		this.frames = frames;
		this.num = num;
		this.rp = rp;
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
		finishTime = Instant.now();
		synchronized (mutex) {
			rp.getTable().add(this);
			int startPosition = rp.getStartPosition();
			setPosition(startPosition);
			rp.setStartPosition(startPosition + 1);
		}
	}
	
	public int getNum() {
		return this.num;
	}
	
	public Instant getFinishTime() {
		return this.finishTime;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return this.position;
	}

}
