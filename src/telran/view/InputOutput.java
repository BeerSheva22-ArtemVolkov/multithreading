package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {

	String readString(String promt);

	void writeString(Object obj);

	default void writeLine(Object obj) {
		writeString(obj.toString() + "\n");
	}

	default <R> R readObject(String promt, String errorPromt, Function<String, R> mapper) {
		boolean running = true;
		R result = null;
		while (running) {
			try {
				String str = readString(promt);
				result = mapper.apply(str);
				running = false;
			} catch (Exception e) {
				writeLine(errorPromt + " - " + e.getMessage());
			}
		}
		return result;
	}

	default String readStringPredicate(String promt, String errorPromt, Predicate<String> predicate) {
		boolean running = true;
		String result = null;
		while (running) {
			try {
				String str = readString(promt);
				if (predicate.test(str)) {
					result = str;
					running = false;
				} else {
					writeLine(errorPromt + " - " + promt + " value does not match the predicate");
				}
			} catch (Exception e) {
				writeLine(errorPromt + " - " + e.getMessage());
			}
		}
		return result;
	}

	default String readStringOptions(String promt, String errorPromt, Set<String> options) {
		boolean running = true;
		String result = "";
		while (running) {
			try {
				String str = readString(promt);
				if (options.contains(str)) {
					result = str;
					running = false;
				} else {
					writeLine(errorPromt + " - " + promt + " not contains in input set");
				}
			} catch (Exception e) {
				writeLine(errorPromt + " - " + e.getMessage());
			}
		}
		return result;
	}

	default int readInt(String promt, String errorPromt) {
		return readObject(promt, errorPromt, Integer::parseInt);	
	}

	default int readInt(String promt, String errorPromt, int min, int max) {
		return readObject(promt, errorPromt, s -> {
			try {
				int res = Integer.parseInt(s);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException();
			}
		});
	}

	default void checkRange(double min, double max, double res) {
		if (res < min) {
			throw new RuntimeException("Number should be greater or equal than " + min);
		}
		if (res > max) {
			throw new RuntimeException("Number should be less or equal than " + max);
		}
	};

	default long readLong(String promt, String errorPromt, long min, long max) {
		return readObject(promt, errorPromt, s -> {
			try {
				long res = Long.parseLong(s);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException();
			}
		});
	}

	default LocalDate readDateISO(String promt, String errorPromt) {
		return readObject(promt, errorPromt, LocalDate::parse);
	}

	default LocalDate readDate(String promt, String errorPromt, String format, LocalDate min, LocalDate max) {
		return readObject(promt, errorPromt, d -> {
			DateTimeFormatter dtf;
			try {
				dtf = DateTimeFormatter.ofPattern(format);
			} catch (Exception e) {
				throw new RuntimeException("Wrong date format" + format);
			}
			LocalDate res = LocalDate.parse(d, dtf);
			if (res.isBefore(min)){
				throw new RuntimeException("most not be before " + min.format(dtf));
			}
			if (res.isAfter(max)) {
				throw new RuntimeException("most not be after " + max.format(dtf));
			}
			return res;
		});
	}

	default double readNumber(String promt, String errorPromt, double min, double max) {
		return readObject(promt, errorPromt, s -> {
			try {
				double res = Double.parseDouble(s);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException();
			}
		});
	}

}
