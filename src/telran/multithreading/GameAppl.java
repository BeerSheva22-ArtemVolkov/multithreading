package telran.multithreading;

import telran.view.*;

public class GameAppl {

	public static void main(String[] args) {

		InputOutput io = new StandardInputOutput();

		Menu main = new Menu("Game", Item.of("Start race", x -> startRun(io), false), Item.exit());

		main.perform(io);
	}

	private static void startRun(InputOutput io) {
		Racer.setDefault();
		Race race = new Race();
		int recersCount = io.readInt("Enter number of racers", "Enormuous count of racers", 2, 20);
		int framesCount = io.readInt("Enter number of frames", "Enormuous count of frames", 10, 1000);
		race.startRace(recersCount, framesCount);
	}

}
