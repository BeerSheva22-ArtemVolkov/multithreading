package telran.garage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import telran.multithreading.util.MyLinkedBlockingQueue;

public class StartWork {

	private static final int N_WORKERS = 20;
	private static final int GARAGE_SIZE = 30;
	
	public static void main(String[] args) throws InterruptedException {
		long halfYear = 8 * 60 * 30 * 6;
		
		MyLinkedBlockingQueue<Car> bq = new MyLinkedBlockingQueue<>(GARAGE_SIZE);
//		BlockingQueue<Car> bq = new ArrayBlockingQueue<>(GARAGE_SIZE);
		Garage garage = new Garage(bq, halfYear);
		garage.start();
		
		List<Worker> workers = new ArrayList<>();
		for (int i = 1; i <= N_WORKERS; i++) {
			Worker worker = new Worker(bq, i);
			worker.start();
			workers.add(worker);
		}
		
		garage.join();
		workers.forEach(x -> x.interrupt());
		workers.forEach(x -> {
			try {
				x.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		System.out.println("Ignored cars : " + garage.getIgnoredCars());
		workers.forEach(x -> System.out.println("Worker [" + x.getNum() + "] fixed " + x.getnFixedCars() + " car(s). And he sleeps " + x.getSleepTime() + " minutes"));
	}

}
