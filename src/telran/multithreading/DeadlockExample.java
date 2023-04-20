package telran.multithreading;

public class DeadlockExample extends Thread {

	public static Object mutex1 = new Object();
	public static Object mutex2 = new Object();

	@Override
	public void run() {

		for (int i = 0; i < 10; i++) {
			synchronized (mutex1) {
				doSomthing();
				synchronized (mutex2) {
					doSomthing();
				}
			}

			synchronized (mutex2) {
				doSomthing();
				synchronized (mutex1) {
					doSomthing();
				}
			}
		}
	}

	private void doSomthing() {
		System.out.println("doing something...");
	}
}
