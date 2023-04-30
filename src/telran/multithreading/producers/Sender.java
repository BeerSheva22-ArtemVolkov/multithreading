package telran.multithreading.producers;

import java.util.concurrent.BlockingQueue;

import telran.multithreading.MessageBox;

public class Sender extends Thread {

//	private MessageBox mb;
	private BlockingQueue<String> mb;
	private int nMessages;

//	public Sender(MessageBox messageBox, int nMessages) {
//		this.mb = messageBox;
//		this.nMessages = nMessages;
//	}

	public Sender(BlockingQueue<String> messageBox, int nMessages) {
		this.mb = messageBox;
		this.nMessages = nMessages;
	}

	@Override
	public void run() {
		for (int i = 1; i <= nMessages; i++) {
			try {
				mb.put("message" + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
