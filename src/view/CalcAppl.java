package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalcAppl {

	public static void main(String[] args) {
		InputOutput io = new StandardInputOutput();

		String dateFormat = "dd.MM.yyyy";

		Item minus = Item.of("+", x -> calculateNumbers(io, "+"));
		Item plus = Item.of("-", x -> calculateNumbers(io, "-"));
		Item multiply = Item.of("*", x -> calculateNumbers(io, "*"));
		Item divide = Item.of("/", x -> calculateNumbers(io, "/"));
		Item plusDays = Item.of("add day(s)", x -> calculateDates(io, dateFormat, "+"));
		Item minusDays = Item.of("minus day(s)", x -> calculateDates(io, dateFormat, "-"));
		Item exit = Item.exit();

		Menu numbers = new Menu("Numbers", plus, minus, multiply, divide, exit);
		Menu dates = new Menu("Dates", plusDays, minusDays, exit);

		Item numberCalc = Item.of("Number operations", x -> numbers.perform(io));
		Item mathCalc = Item.of("Dates operations", x -> dates.perform(io));

		Menu mainMenu = new Menu("Calculator", numberCalc, mathCalc, exit);

		mainMenu.perform(io);
	}

	private static void calculateDates(InputOutput io, String dateFormat, String type) {
		LocalDate date = io.readDate("Type date in format " + dateFormat, "Incorrect date", dateFormat,
				LocalDate.MIN, LocalDate.MAX);
		int quantity = io.readInt("Enter a number", "Incorrect number");
		LocalDate res = LocalDate.MIN;
		switch (type) {
		case "+":
			res = date.plusDays(quantity);
			break;
		case "-":
			res = date.minusDays(quantity);
			break;
		}
		io.writeLine(date.format(DateTimeFormatter.ofPattern(dateFormat)) + (type.equals("+") ? " + " : " - ") + quantity
				+ " day(s) = " + res.format(DateTimeFormatter.ofPattern(dateFormat)));
	}

	private static void calculateNumbers(InputOutput io, String sign) {
		double i1 = io.readNumber("Enter the first number", "Incorrect number", -Double.MAX_VALUE, Double.MAX_VALUE);
		double i2 = io.readNumber("Enter the second number", "Incorrect number", -Double.MAX_VALUE, Double.MAX_VALUE);
		double res = 0;
		switch (sign) {
		case "+":
			res = i1 + i2;
			break;
		case "-":
			res = i1 - i2;
			break;
		case "*":
			res = i1 * i2;
			break;
		case "/":
			res = i1 / i2;
			break;
		}
		io.writeLine(i1 + " " + sign + " " + i2 + " = " + res);
	}
}
