package telran.multithreading;

public class Deadlock {

	public static void main(String[] args) throws InterruptedException {
//		Thread t1 = new DeadlockExample();
//		Thread t2 = new DeadlockExample();
//		t1.start();
//		t2.start();
		X x1 = new X();
		X x2 = new X();
		x1.start();
		x2.start();
	}
}
