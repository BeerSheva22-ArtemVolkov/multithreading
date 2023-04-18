package telran.multithreading;

import java.time.Instant;
import java.util.ArrayList;

public class RaceParams {

	public ArrayList<Racer> table = new ArrayList<>();
	public Instant startTime = Instant.now();
	public int startPosition = 1;

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

}
