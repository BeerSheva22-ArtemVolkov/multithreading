package telran.multithreading;

public class Truck extends Thread {

	private int load; // Грузоподъемность
	private static long elevator1; // Хранилище
	private static long elevator2;
	private int nRuns; // Количество поездок грузовика

	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			loadElevator1();
			loadElevator2();
		}
	}

	private void loadElevator1() {
		elevator1 += load;
	}

	private void loadElevator2() {
		elevator2 += load;
	}
	
	public static long getElevator1() {
		return elevator1;
	}

	public static long getElevator2() {
		return elevator2;
	}
	
}
