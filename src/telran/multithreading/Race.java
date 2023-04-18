package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Race {

	void startRace(int racersCount, int framesCount) {

		Racer[] racers = new Racer[racersCount];

		Racer.startTime = Instant.now();
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
		
		for (Racer racer : Racer.getTable()) {
			System.out.println(racer.getPosition() + "___" + racer.getNum() + "___" + (ChronoUnit.MILLIS.between(Racer.startTime, racer.getFinishTime())));
		}
		
	}
}
