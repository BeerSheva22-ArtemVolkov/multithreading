package telran.multithreading;

import java.time.Instant;
import java.util.ArrayList;

public class RaceParams {

	private ArrayList<Racer> table = new ArrayList<>();
	private Instant startTime = Instant.now();
	private int startPosition = 1;
	private int frames;
	
	public RaceParams(int frames) {
		this.frames = frames;
	}

	public ArrayList<Racer> getTable() {
		return table;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getFrames() {
		return frames;
	}
}
