package telran.multithreading;

import java.time.Instant;
import java.util.Random;

public class Racer extends Thread {

	final int MIN_SLEEP = 2;
	final int MAX_SLEEP = 5;
	
	private int racerNumber;
	private Object mutex = new Object();
	private int position;
	private Instant finishTime;
	private RaceParams rp;

	public Racer(int racerNumber, RaceParams rp) {
		this.racerNumber = racerNumber;
		this.rp = rp;
	}

	@Override
	public void run() {
		for (int i = 0; i < rp.getFrames(); i++) {
			Random random = new Random();
			try {
				sleep(random.nextInt(MIN_SLEEP, MAX_SLEEP + 1));
			} catch (InterruptedException e) {
				// Кто-то разбудил
			}
		}
		
		synchronized (mutex) {
			finishTime = Instant.now();
			rp.getTable().add(this);
			int startPosition = rp.getStartPosition();
			position = startPosition;
			rp.setStartPosition(startPosition + 1);
		}
	}
	
	public int getNum() {
		return racerNumber;
	}
	
	public Instant getFinishTime() {
		return finishTime;
	}
	
	public int getPosition() {
		return position;
	}

}
