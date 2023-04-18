package telran.multithreading.task;

import java.util.Scanner;

public class SymbolsControllerAppl {

	public static void main(String[] args) {
		SymbolsPrinter sp = new SymbolsPrinter("cuphead");
		sp.start();

		Scanner in = new Scanner(System.in);

		while (true) {
			String letter = in.nextLine();
			if (letter.toLowerCase().equals("q")) {
				break;
			} else {
				sp.interrupt();
			}
		}

		in.close();
	}

}
