package telran.multithreading.synch;

public class PrintThreads extends Thread {

	private int threadNum;
	private int size;
	private int countRepeat;
	private int count = 0;

	private Thread next; 

	public PrintThreads(int threadNum, int size, int countRepeat) {
		this.threadNum = threadNum;
		this.size = size;
		this.countRepeat = countRepeat;
	}

	public void setNext(Thread next) {
		this.next = next;
	}

	@Override
	public void run() {
		while (count < size) {
			try {
				sleep(1);
			} catch (InterruptedException e) {
				System.out.println("Step [" + count++ + "]__" + Integer.toString(threadNum + 1).repeat(countRepeat));
				next.interrupt();
			}
		}
	}

}
