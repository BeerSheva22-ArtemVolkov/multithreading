package telran.multithreading.producers;

import telran.multithreading.MessageBox;

public class Sender extends Thread {

	private MessageBox evenBox;
	private MessageBox oddBox;
	private int nMessages;

	public Sender(MessageBox messageBox, MessageBox messageBox2, int nMessages) {
		this.evenBox = messageBox;
		this.oddBox = messageBox2;
		this.nMessages = nMessages;
	}

	@Override
	public void run() {
		for (int i = 1; i <= nMessages; i++) {
			put(i % 2 == 0 ? evenBox : oddBox, "message" + i);
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void put(MessageBox mb, String message) {
		mb.put(message);
	}
}
