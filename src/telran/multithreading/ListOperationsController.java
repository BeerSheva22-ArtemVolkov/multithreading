package telran.multithreading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;
import java.util.stream.IntStream;

public class ListOperationsController {

	private static final int N_NUMBERS = 1000;
	private static final int N_THREADS = 100;
	private static final int UPDATE_PROB = 0;
	private static final int N_RUNS = 1000;

	public static void main(String[] args) {
		Integer[] numbers = new Integer[N_NUMBERS];
		Arrays.fill(numbers, 100);
		List<Integer> list = new LinkedList<>(Arrays.asList(numbers));
		ListOperations[] operations = new ListOperations[N_THREADS];
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		Lock readLock = lock.readLock();
		Lock writeLock = lock.writeLock();
		AtomicInteger count = new AtomicInteger(0);
		
		IntStream.range(0, N_THREADS).forEach(i -> {
			operations[i] = new ListOperations(UPDATE_PROB, list, N_RUNS, readLock, writeLock, count);
			operations[i].start();
		});
		
		Arrays.stream(operations).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		});
		System.out.println("Total number of iterations fpor waiting lock opened " + count);
	}

}
