package de.lechner.readslip.p2p;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.lechner.readslip.bon.SlipEntryList;

@RestController
public class P2PController {
    @Autowired
    P2PService rs;

    @GetMapping(value = "/p2p")
    public String netto() {

        return "Nothing to return";
    }

     @RequestMapping(method=RequestMethod.POST, value="/p2p")
          public String addTransaction(@RequestBody  SlipEntryList  se , @RequestHeader("company") String company) {
                // transactionservice.addTransaction(transaction);
           rs.analyseRest(se, company); 
           return "accept";
    }     
          

}