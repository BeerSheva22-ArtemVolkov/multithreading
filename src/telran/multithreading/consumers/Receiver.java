package telran.multithreading.consumers;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {

	private MessageBox messageBox;
	
	public Receiver(MessageBox messageBox) {
		this.messageBox = messageBox;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				String message = messageBox.take();
				System.out.printf("thread: %s; received message: %s\n", getName(), message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
