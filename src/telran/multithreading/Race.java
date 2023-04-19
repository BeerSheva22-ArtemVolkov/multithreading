package telran.multithreading;

import java.time.temporal.ChronoUnit;

public class Race {

	void startRace(int racersCount, int framesCount) {

		Racer[] racers = new Racer[racersCount];

		RaceParams rp = new RaceParams(framesCount);
		for (int i = 0; i < racersCount; i++) {
			Racer racer = new Racer(i + 1, rp);
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
		
		for (Racer racer : rp.getTable()) {
			System.out.println(racer.getPosition() + "___" + racer.getNum() + "___" + (ChronoUnit.MILLIS.between(rp.getStartTime(), racer.getFinishTime())));
		}
		
	}
}
