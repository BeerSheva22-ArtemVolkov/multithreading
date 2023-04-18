package telran.multithreading;

public class TimeTestAppl {

	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.start();
		// running imitation
		
		Thread.sleep(5000); // 5 сек выполняется Timer
		timer.interrupt(); // прерывает поток Timer
		Thread.sleep(5000); // 5 сек после выполнения Timer
		
//		Thread mainThread = Thread.currentThread();
//		mainThread.interrupt();
//		mainThread.join();
		
		System.out.println("Конец main");
	}
}