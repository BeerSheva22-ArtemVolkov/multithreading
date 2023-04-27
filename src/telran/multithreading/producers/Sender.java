package telran.multithreading.producers;

import telran.multithreading.MessageBox;

public class Sender extends Thread {

	private MessageBox mb;
	private int nMessages;

	public Sender(MessageBox messageBox, int nMessages) {
		this.mb = messageBox;
		this.nMessages = nMessages;
	}

	@Override
	public void run() {
		for (int i = 1; i <= nMessages; i++) {
			put(mb, "message" + i);
		}
	}
	
	private void put(MessageBox mb, String message) {
		try {
			mb.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
