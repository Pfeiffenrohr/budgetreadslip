package de.lechner.readslip.infrastructure;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.lechner.readslip.readfile.BonServiceReadFile;

@Service
public class BonReadServiceCron {
	
	@Autowired
	BonServiceReadFile bonService; 
	
	 public void sayHello() {
	        System.out.println("Execute readBon " + new Date());
	    }
	 
	 public void readBon() {
		 bonService.handleSlipFile();
	 }
	 
	 public void updateTrancaction() {
		 bonService.updateOldTransactions();
	 }
	
}	 