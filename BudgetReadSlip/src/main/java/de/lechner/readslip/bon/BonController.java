package de.lechner.readslip.bon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.lechner.readslip.readfile.BonServiceReadFile;

@RestController
public class BonController {
	private static final Logger LOG = LoggerFactory.getLogger(BonController.class);
	@Autowired
	BonServiceReadFile rs;

	@GetMapping(value = "/netto")
	public String netto() {

		rs.handleSlipFile();
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
		   LOG.info("accept "+ company);
		   rs.handleSlipRest(se, company);
		   return "accept";
	}	  
		  

}
