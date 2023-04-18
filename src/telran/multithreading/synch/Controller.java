package telran.multithreading.synch;

import telran.view.InputOutput;
import telran.view.StandardInputOutput;

public class Controller {

	public static void main(String[] args) {
		
		InputOutput io = new StandardInputOutput();
		int threadsCount = io.readInt("Type threads count from 2 to 9", "Incorrect number of threads", 2, 9);
		PrintThreads[] threadList = new PrintThreads[threadsCount];
		int iterationsCount = io.readInt("Type count of steps from 2 to 100", "Incorrect count of steps", 2, 100);
		int repeatsCount = io.readInt("How much time thread number should be repeated? (from 2 to 10)", "Incorrect count of repeats", 2, 10);
		
		for (int i = 0; i < threadsCount; i++) {
			threadList[i] = new PrintThreads(i, iterationsCount, repeatsCount);
		}
		
		for (int i = 0; i < threadsCount; i++) {
			if (i < threadsCount - 1) {
				threadList[i].setNext(threadList[i + 1]);
			}
			if (i == threadsCount - 1) {
				threadList[i].setNext(threadList[0]);
			}
		}
		
		for (PrintThreads th : threadList) {
			th.start();
		}
		
		threadList[0].interrupt();
		
	}
}
