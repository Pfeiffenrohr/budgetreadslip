package de.lechner.readslip.fonds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.lechner.readslip.bon.SlipEntryList;
import de.lechner.readslip.readfile.BonServiceReadFile;
import org.springframework.web.client.RestTemplate;

@RestController
public class FondsController {
    @Autowired
    FondsService rs;

    @GetMapping(value = "/fonds")
    public String netto() {

        return "Nothing to return";
    }

     @RequestMapping(method=RequestMethod.POST, value="/fonds")
          public String addTransaction(@RequestBody  SlipEntryList  se , @RequestHeader("company") String company) {
                // transactionservice.addTransaction(transaction);
           System.out.println("accept "+ company);
           rs.analyseRest(se, company); 
           return "accept";
    }     
          

}