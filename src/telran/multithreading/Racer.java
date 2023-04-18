package telran.multithreading;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class Racer extends Thread {

	final int MIN_SLEEP = 2;
	final int MAX_SLEEP = 5;
	
	private int frames;
	private int num;
	private Object mutex = new Object();
	private int position;
	private Instant finishTime;
	
	private static ArrayList<Racer> table;
	public static Instant startTime;
	private static int startPosition;

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
		finishTime = Instant.now();
		synchronized (mutex) {
			table.add(this);
			setPosition(startPosition++);
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
	
	static public void setDefault() {
		startTime = null;
		startPosition = 1;
		table = new ArrayList<>();
	}

	public static ArrayList<Racer> getTable() {
		return table;
	}

}
