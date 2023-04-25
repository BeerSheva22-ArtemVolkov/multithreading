package telran.multithreading;

import telran.multithreading.consumers.Receiver;
import telran.multithreading.producers.Sender;

public class SenderReceiversAppl {

	private static final int N_MESSAGES = 21;
	private static final int N_RECEIVERS = 11;

	public static void main(String[] args) throws InterruptedException {
		MessageBox evenBox = new MessageBox();
		MessageBox oddBox = new MessageBox();
		Sender sender = new Sender(evenBox, oddBox, N_MESSAGES);
		sender.start();
		for (int i = 0; i < N_RECEIVERS; i++) {
			new Receiver(i % 2 == 0 ? evenBox : oddBox).start();
		}
		sender.join();
	}
	
}
