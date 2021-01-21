package de.lechner.readslip.slip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlipController {
	@Autowired
	SlipService rs;

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
