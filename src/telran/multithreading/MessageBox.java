package telran.multithreading;

public class MessageBox {

	String message;

	// Consumer, sender
	synchronized public void put(String message) throws InterruptedException { // sync по this(messageBox)
		while (this.message != null) {
			wait();
		}
		this.message = message;
		notifyAll();
	}

	// Producer, receiver
	synchronized public String take() throws InterruptedException {
		while (message == null) {
			wait();
		}
		String res = message;
		message = null;
		notifyAll();
		return res;
	}
}
