package telran.view;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

import telran.employee.controller.CompanyControllerItems;
import telran.employee.net.NetworkCompanyImpl;
import telran.net.NetworkClient;

public class CompanyClientConfigAppl {

	private static final String BASE_PACKAGE = "telran.net.";
//	private static final String CONFIG_PATH = "config.txt";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		if (args.length != 1) {
			throw new RuntimeException("Unacceptable count of arguments");
		}

		InputOutput io = new StandardInputOutput();

		Properties prop = new Properties();
		try (FileInputStream fis = new FileInputStream(args[0])) {
			prop.load(fis);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException("File not found");
		}

		// Get properties from config
		String className = "";
		String host = "";
		int port = 0;
		String[] departmentItems = new String[] {};
		try {
			className = prop.getProperty("transport");
			host = prop.getProperty("hostname");
			port = Integer.parseInt(prop.getProperty("port"));
			departmentItems = Arrays.stream(prop.getProperty("departments").split(",")).map(String::trim)
					.toArray(String[]::new);
		} catch (NumberFormatException e) {
			System.out.println("Errors while reading properties " + e.getMessage());
		}

		if (className.isEmpty() || host.isEmpty() || port == 0 || departmentItems.length == 0) {
			System.out.println("One of properties is empty");
		} else {
			// Init
			NetworkClient client = null;
			try {
				Class<NetworkClient> networkClientClass = (Class<NetworkClient>) Class
						.forName(BASE_PACKAGE + className.toLowerCase() + "." + className + "Client");
				Constructor<NetworkClient> networkClientConstructor = networkClientClass.getConstructor(String.class,
						int.class);
				client = networkClientConstructor.newInstance(host, port);
			} catch (Exception e) {
				System.out.println("Trouble with building class " + e.getMessage());
			}
			if (client != null) {
				// Standard
				NetworkCompanyImpl company = new NetworkCompanyImpl(client);
				Item[] companyItems = CompanyControllerItems.getCompanyItems(company, departmentItems);
				ArrayList<Item> items = new ArrayList<>(Arrays.asList(companyItems));
				items.add(Item.of("Exit & save", io1 -> {
				}, true));
				Menu menu = new Menu("Company Application", items);

				menu.perform(io);
				io.writeLine("Company saved. Goodbuy!");
			}
		}

	}

}
