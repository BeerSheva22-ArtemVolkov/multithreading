package telran.multithreading;

import java.time.Instant;
import java.util.Random;

public class Racer extends Thread {
	
	private int racerNumber;
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
				sleep(random.nextInt(rp.MIN_SLEEP, rp.MAX_SLEEP + 1));
			} catch (InterruptedException e) {
				// Кто-то разбудил
			}
		}
		
		synchronized (rp) {
			finishTime = Instant.now();
			rp.getTable().add(this);
			position = rp.getStartPosition();
			rp.setStartPosition(position + 1);
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
