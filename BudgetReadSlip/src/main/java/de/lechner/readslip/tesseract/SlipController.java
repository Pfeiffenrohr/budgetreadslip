package de.lechner.readslip.tesseract;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlipController {
	
	
	 @GetMapping(value = "/hi")
	public String sayHi() {
		 ReadSlip rs = new ReadSlip();
		 rs.readTesseract();
		return "hi";
	}

}
