package telran.multithreading.consumers;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {

	private MessageBox messageBox;
	
	public Receiver(MessageBox messageBox) {
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
					message = messageBox.get();
					if (message != null) {
						System.out.printf("thread: %s; received message: %s\n", getName(), message);
					}
				} while (message != null);
				break;
			}
		}
	}
	
}
