package de.lechner.readslip.bon;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonReadServiceCron {
	
	@Autowired
	BonService bonService; 
	
	 public void sayHello() {
	        System.out.println("Execute readBon " + new Date());
	    }
	 
	 public void readBon() {
		 bonService.handleSlip();
	 }
}	 