package telran.multithreading;

public class Truck extends Thread {

	private int load; // Грузоподъемность
	private static long elevator1; // Хранилище
	private static long elevator2;
	private final static Object mutex = new Object();
//	private final static Object mutex2 = new Object();
	private int nRuns; // Количество поездок грузовика
	
	public Truck(int load, int nRuns) {
		this.load = load;
		this.nRuns = nRuns;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			loadElevator1(load);
			loadElevator2(load);
		}
	}

	// mutex - объект класса синхронизации
	static private void loadElevator1(int load) {
		synchronized (mutex) {
			elevator1 += load;
		}
	}

	// synchronized method - 
	synchronized static private void loadElevator2(int load) { // synchronized static блокирует доступ для других экземпляра класса
		elevator2 += load;
	}
	
	public static long getElevator1() {
		return elevator1;
	}

	public static long getElevator2() {
		return elevator2;
	}
	
}
