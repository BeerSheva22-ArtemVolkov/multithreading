package telran.multithreading;

public class MessageBox {

	String message;

	// Consumer
	synchronized public void put(String message) { // sync по this(messageBox)
		this.message = message;
		this.notify(); // можно без this
	}

	// Producer
	synchronized public String take() throws InterruptedException {
		// не if потому что
		while (message == null) {
			wait();
		}
		String res = message;
		message = null;
		return res;

	}
}
