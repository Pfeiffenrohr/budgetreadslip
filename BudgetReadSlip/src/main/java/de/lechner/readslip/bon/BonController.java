package de.lechner.readslip.bon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
