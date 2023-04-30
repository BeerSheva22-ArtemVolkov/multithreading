package telran.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import telran.multithreading.consumers.Receiver;
import telran.multithreading.producers.Sender;
import telran.multithreading.util.MyLinkedBlockingQueue;

public class SenderReceiversAppl {

	private static final int N_MESSAGES = 100;
	private static final int N_RECEIVERS = 10;

	public static void main(String[] args) throws InterruptedException {
//		MessageBox mb = new MessageBox();
		BlockingQueue<String> mb = new LinkedBlockingQueue<>(1);
		Sender sender = new Sender(mb, N_MESSAGES);
		sender.start();
		List<Receiver> receivers = new ArrayList<>();
		for (int i = 0; i < N_RECEIVERS; i++) {
			Receiver rec = new Receiver(mb);
			rec.start();
			receivers.add(rec);
		}
		sender.join();
		receivers.forEach(x -> x.interrupt());
	}
	
}