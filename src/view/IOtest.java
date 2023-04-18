package telran.view;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class IOtest {

	StandardInputOutput InputOutput = new StandardInputOutput();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Disabled
	@Test
	void readObjecttest() {
		String end = "@google.com";
		assertNotNull(InputOutput.readObject("Enter login to email", "Some error", f -> f + end));
	}

	@Disabled
	@Test
	void readStringPredicatetest() {
		assertNotNull(InputOutput.readStringPredicate("Enter Email", "Incorrect mail", str -> str.contains("@")));
	}

	@Disabled
	@Test
	void readStringOptionstest() {
		HashSet<String> set = new HashSet<String>(Arrays.asList("Artem", "Egor", "Pavel"));
		assertNotNull(InputOutput.readStringOptions("Select one: " + set.toString(), "Some error", set));
	}

	@Disabled
	@Test
	void readInttest() {
		assertNotNull(InputOutput.readInt("Enter a number", "Invalid number"));
	}

	@Disabled
	@Test
	void readIntMinMaxtest() {
		int min = 0;
		int max = 100;
		assertNotNull(InputOutput.readInt("Enter a number between " + min + " and " + max, "Incorrect number", 1, 100));
	}

	@Disabled
	@Test
	void readLongtest() {
		long min = 0;
		long max = 100000000L;
		assertNotNull(InputOutput.readLong("Enter a number in Long between " + min + " and " + max, "Error in Long",
				min, max));
	}

	@Disabled
	@Test
	void readDateISOtest() {
		assertNotNull(InputOutput.readDateISO("Enter a date", "Incorrect date"));
	}

	@Disabled
	@Test
	void readDatetest() {
		LocalDate min = LocalDate.of(2022, 1, 1);
		LocalDate max = LocalDate.of(2023, 1, 1);
		String format = "dd.MM.yyyy";
		assertNotNull(InputOutput.readDate("Enter a date between " + min + " and " + max + " in format " + format,
				"Incorrect date", format, min, max));
	}

	@Test
	void readNumbertest() {
		int min = 0;
		int max = 1000;
		assertNotNull(
				InputOutput.readNumber("Enter a number between " + min + " and " + max, "double error", min, max));
	}

}
