package de.lechner.readslip.bon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonController {
	@Autowired
	BonService rs;

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

}
