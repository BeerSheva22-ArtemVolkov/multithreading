package telran.garage;

import java.util.Random;

public class Car {

	private static final long TIME_MINIMUM = 30;
	private static final long TIME_MAXIMUM = 480;

	public long fixTime;

	public Car() {
		Random random = new Random();
		fixTime = random.nextLong(TIME_MAXIMUM - TIME_MINIMUM) + TIME_MINIMUM;
	}
	
}
