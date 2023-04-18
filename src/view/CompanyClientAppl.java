package telran.view;

import java.util.*;

import telran.employee.net.NetworkCompanyImpl;
import telran.net.NetworkClient;
import telran.net.tcp.TcpClient;
import telran.employee.controller.CompanyControllerItems;

public class CompanyClientAppl {

	public static void main(String[] args) throws Exception {
		
		InputOutput io = new StandardInputOutput();
		
		NetworkClient client = new TcpClient("localhost", 4000);
		String[] departmentItems = {"QA", "Development", "Audit", "Accounting"};
		
		NetworkCompanyImpl company = new NetworkCompanyImpl(client);

//		Menu menu = new CompanyControllerItems(company).getMainMenuItems(); //My
		
		Item[] companyItems = CompanyControllerItems.getCompanyItems(company, departmentItems);
		ArrayList<Item> items = new ArrayList<>(Arrays.asList(companyItems));
		items.add(Item.of("Exit & save", io1 -> {}, true));
		Menu menu = new Menu("Company Application", items);
		
		menu.perform(io);
		io.writeLine("Company saved. Goodbuy!");
	}
	
}
