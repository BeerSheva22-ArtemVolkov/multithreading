package telran.view.test;

import static telran.view.Item.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import telran.employee.*;
import telran.view.*;

public class CompanyControllerItems {

	private static Company company;

	private static String format = "dd.MM.yyyy";
	private static Set<String> departments = new HashSet<String>(Arrays.asList("HR", "Dev", "Stuff"));
	
	private static Item addEmployee = Item.of("Add new Employee", CompanyControllerItems::addEmployee);
	private static Item removeEmployee = Item.of("Remove Employee", CompanyControllerItems::removeEmployee);
	private static Item getAllEmployees = Item.of("Get list of employees", CompanyControllerItems::getAllEmployees);
	private static Item getEmployeesByMonthBirth = Item.of("Select employees by month birth",
			CompanyControllerItems::getEmployeesByMonthBirth);
	private static Item getEmployeesBySalary = Item.of("Select employees by salary", CompanyControllerItems::getEmployeesBySalary);
	private static Item getEmployeesByDepartment = Item.of("Select employees by department",
			CompanyControllerItems::getEmployeesByDepartment);
	private static Item getEmployee = Item.of("Get employee by id", CompanyControllerItems::getEmployee);

	public CompanyControllerItems(Company company) {
		this.company = company;
	}

	public Menu getMainMenuItems() {
		return new Menu("Company main menu", UserMenu(), AdminMenu(), exit());
	}

	static Menu AdminMenu() {
		return new Menu("Admin menu", addEmployee, removeEmployee, getAllEmployees, getEmployeesByMonthBirth,
				getEmployeesBySalary, getEmployeesByDepartment, getEmployee, exit());
	}

	static Menu UserMenu() {
		return new Menu("User menu", getAllEmployees, getEmployeesByMonthBirth, getEmployeesBySalary,
				getEmployeesByDepartment, getEmployee, exit());
	}

	private static void addEmployee(InputOutput io) {
		long id = selectEmplID(io);
		String name = io.readObject("Enter user name", "Unacceptable name", t -> t.toString());
		LocalDate date = io.readDate("Enter birhtdate in " + format, "Unacceptable age",
				format, LocalDate.now().minusYears(100), LocalDate.now().minusYears(18));
		String department = io.readStringOptions("Select department " + departments.toString(),
				"There is no depurtment with this name", departments);
		int salary = io.readInt("Enter user's salary", "We have no such salary", 5571, 30000);
		company.addEmployee(new Employee(id, name, date, department, salary));
	}

	private static void removeEmployee(InputOutput io) {
		HashSet<String> set = new HashSet<>();
		company.getAllEmployees().forEach(x -> set.add(String.valueOf(x.getId())));
		long id = Long.parseLong(io.readStringOptions("Enter user ID", "There is no user with this id", set));
		company.removeEmployee(id);
		io.writeLine("User with id [" + id + "] was removed");
	}

	private static void getAllEmployees(InputOutput io) {
		List<Employee> list = company.getAllEmployees();
		printEmployees(io, list);
	}

	private static void getEmployeesByMonthBirth(InputOutput io) {
		int month = io.readInt("Select month of birth", "Unacceptable month", 1, 12);
		List<Employee> list = company.getEmployeesByMonthBirth(month);
		printEmployees(io, list);
	}

	private static void getEmployeesBySalary(InputOutput io) {
		int salaryFrom = io.readInt("Enter minimum salary", "We haven't such salary", 5571, 30000);
		int salaryTo = io.readInt("Enter maximum salary",
				"We haven't such salary, or you wrote less than minimum salary", salaryFrom, 30000);
		List<Employee> list = company.getEmployeesBySalary(salaryFrom, salaryTo);
		printEmployees(io, list);
	}

	private static void getEmployeesByDepartment(InputOutput io) {
		String department = io.readStringOptions("Select department " + departments.toString(), "There is no such department", departments);
		List<Employee> list = company.getEmployeesByDepartment(department);
		printEmployees(io, list);
	}

	private static void getEmployee(InputOutput io) {
		boolean running = true;
		long id = 0;
		Employee empl;
		while (running) {
			id = io.readLong("Enter user ID", "Unacceptable user id", 1, Integer.MAX_VALUE);
			if ((empl = company.getEmployee(id)) != null) {
				printEmployees(io, new ArrayList<Employee>(Arrays.asList(empl)));
				running = false;
			} else {
				io.writeLine("There is already have such id");
			}
		}
	}

	private static void printEmployees(InputOutput io, List<Employee> list) {
		if (list.size() == 0) {
			io.writeLine("No users with your parapeters");
		}
		list.forEach(e -> io.writeLine(	e.getId() + " " + 
										e.getName() + " " + 
										e.getBirthDate().format(DateTimeFormatter.ofPattern(format)) + " " + 
										e.getDepartment() + " " + 
										e.getSalary()));
	}

	private static long selectEmplID(InputOutput io) {
		boolean running = true;
		long id = 0;
		while (running) {
			id = io.readLong("Enter user ID", "Unacceptable ID", 1, Integer.MAX_VALUE);
			if (company.getEmployee(id) == null) {
				running = false;
			} else {
				io.writeLine("There is already have such id");
			}
		}
		return id;
	}

}
