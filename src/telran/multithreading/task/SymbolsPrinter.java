package telran.multithreading.task;

public class SymbolsPrinter extends Thread {

	private String symbols;
	private int count = 0;
	private boolean running = true;

	public SymbolsPrinter(String symbols) {
		this.symbols = symbols;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (running) {
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				if (++count == symbols.length()) {
					count = 0;
				}
			}
			System.out.println(symbols.charAt(count));
		}
	}
}
