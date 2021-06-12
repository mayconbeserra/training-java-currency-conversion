package com.scalablecapital.currencyservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.math.BigDecimal;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.scalablecapital.currencyservice.services.CentralBankService;
import com.scalablecapital.currencyservice.services.ECBException;
import com.scalablecapital.currencyservice.services.ECBService;
import com.scalablecapital.currencyservice.services.FXService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyserviceApplication {

	public static void main(String[] args) throws MalformedURLException, ECBException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			var ecb = new ECBService(new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"));
			var centralBankServices = new ArrayList<CentralBankService>();

			centralBankServices.add(ecb);
			var fxService = new FXService(centralBankServices);

			var result = fxService.convert("USD", "BRL", new BigDecimal(100));
			result.toString();

			// now XML is loaded as Document in memory, lets convert it to Object List
			// List<Employee> empList = new ArrayList<Employee>();
			// for (int i = 0; i < nodeList.getLength(); i++) {
			// empList.add(getEmployee(nodeList.item(i)));
			// }
			// //lets print Employee list information
			// for (Employee emp : empList) {
			// System.out.println(emp.toString());
			// }
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		SpringApplication.run(CurrencyserviceApplication.class, args);
	}

}
