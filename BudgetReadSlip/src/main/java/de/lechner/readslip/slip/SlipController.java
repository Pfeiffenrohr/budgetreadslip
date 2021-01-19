package de.lechner.readslip.slip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlipController {
	@Autowired
	SlipService rs; 
	
	 @GetMapping(value = "/hi")
	public String sayHi() {
		
		 rs.handleSlip();
		return "hi";
	}

}
