package telran.multithreading;

public class Race {

	void startRace(int racersCount, int framesCount) {

		Racer[] racers = new Racer[racersCount];

		for (int i = 0; i < racersCount; i++) {
			Racer racer = new Racer(framesCount, i + 1);
			racer.start();
			racers[i] = racer;
		}

		for (Racer racer : racers) {
			try {
				racer.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
