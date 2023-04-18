package telran.view;

import java.util.ArrayList;
import java.util.Arrays;

import telran.employee.Company;
import telran.employee.CompanyImpl;
import telran.employee.controller.CompanyControllerItems;

public class CompanyAppl {

	private static String path = "CompanyController.txt";
	
	public static void main(String[] args) {
		
		
		
		InputOutput io = new StandardInputOutput();
		Company company = new CompanyImpl();
		
		company.restore(path);
		
		Item[] companyItems = CompanyControllerItems.getCompanyItems(company, new String[] {"QA", "Development", "Audit", "Accounting"});
		ArrayList<Item> items = new ArrayList<>(Arrays.asList(companyItems));
		items.add(Item.of("Exit & save", io1 -> company.save(path), true));
		Menu menu = new Menu("Company Application", items);
//		Menu menu = new CompanyControllerItems(company).getMainMenuItems();
		menu.perform(io);
		
		io.writeLine("Company saved. Goodbuy!");
		company.save(path);
		
	}

}
