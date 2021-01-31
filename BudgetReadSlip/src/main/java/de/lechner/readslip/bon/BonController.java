package de.lechner.readslip.bon;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.lechner.readslip.readfile.BonServiceReadFile;

@RestController
public class BonController {
	@Autowired
	BonServiceReadFile rs;

	@GetMapping(value = "/netto")
	public String netto() {

		rs.handleSlip();
		return "netto";
	}

	@GetMapping(value = "/update")
	public String update() {

		rs.updateOldTransactions();
		return "update";
	}
	
	 @RequestMapping(method=RequestMethod.POST, value="/bon")
		  public String addTransaction(@RequestBody  SlipEntryList  se , @RequestHeader("company") String company) {
				// transactionservice.addTransaction(transaction);
		   System.out.println("accept "+ company);
		   return "accept";
	}	  
		  

}
