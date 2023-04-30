package telran.multithreading.consumers;

import java.util.concurrent.BlockingQueue;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {

//	private MessageBox messageBox;
	private BlockingQueue<String> messageBox;

//	public Receiver(MessageBox messageBox) {
//		this.messageBox = messageBox;
//	}

	public Receiver(BlockingQueue<String> messageBox) {
		this.messageBox = messageBox;
	}

	@Override
	public void run() {
		while (true) {
			String message;
			try {
				message = messageBox.take();
				System.out.printf("thread: %s; received message: %s\n", getName(), message);
			} catch (InterruptedException e) {
				do {
					message = messageBox.poll();
					if (message != null) {
						System.out.printf("thread: %s; received message: %s\n", getName(), message);
					}
				} while (message != null);
				break;
			}
		}
	}

}
