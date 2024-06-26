package de.lechner.readslip.p2p;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.lechner.readslip.bon.SlipEntry;
import de.lechner.readslip.bon.SlipEntryList;
import de.lechner.readslip.message.Budget;
import de.lechner.readslip.parser.Transaction;

@Service
public class P2PService {
    
    @Value("${budgetserver.host}")
    private String host;
    @Value("${budgetserver.port}")
    private String port;
    @Autowired
    Budget budget;

    private static final Logger LOG = LoggerFactory.getLogger(P2PService.class);
    public void analyseRest(SlipEntryList listSE,String company) {
        List <SlipEntry>  list = listSE.getList();
        
        parseP2P(list,company);
        
        
    }

   public void  parseP2P (List  <SlipEntry> list,String company) {
       
       //Vorerst haben wir nur Gesamtertrag. Daher ist die Liste evtl überflüssig
       //Aber vielleicht bekommen wir in Zukunft ja mehr
       if (list.size()<1)
       {
           //Liste ist leer. kein richtiger Wert bekommen
           return;
       }
       String ertrag =  list.get(0).getSum();
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Calendar calnow = Calendar.getInstance();
       String enddate=formatter.format(calnow.getTime());
       //Hole den Ertrag, den wir bisher sschon haben.
       UriComponents uriComponents = UriComponentsBuilder.newInstance()
               .scheme("http").host(host).port(port).path("/transaction_get_sum")
               .queryParam("startdate", "2011-01-01")
               .queryParam("enddate", enddate)
               .queryParam("categorie", 42)
               .queryParam("konto", budget.getKontoByName(company,host,port))
               .build();
       //Für Robocach holen wir den Gesamtbestand des Kontos, nicht den Ertrag
       if (company.equals("Robocash") || company.equals("Twino"))
       {
           uriComponents = UriComponentsBuilder.newInstance()
                   .scheme("http").host(host).port(port).path("/transaction_get_sum")
                   .queryParam("startdate", "2011-01-01")
                   .queryParam("enddate", enddate)
                   .queryParam("konto", budget.getKontoByName(company,host,port))
                   .build();
       }
                
       String uri=uriComponents.toUriString();
       RestTemplate restTemplate = new RestTemplate();
       String result = restTemplate.getForObject(uri, String.class);
       if (result==null ||result.equals(""))
       {
          LOG.warn("No result found. Set result to 0");
          result="0.0";
       }
       LOG.info("Found " +  result +" amount now");
       
       double diff;
       //Extrawurst für viainvest
       if ( company.equals("ViaInvest")||company.equals("Income")||company.equals("Swaper"))
       {
    	   diff=new Double (ertrag);
       }
    		   
       else
       {
    	   diff = new Double (ertrag) - new Double (result);
       }
       LOG.info("Found to insert " + diff);
       //Falls der Betrag größer 49€ oder kleiner -49 € ist, dann wird er vorerst nicht eingetragen
       // Ansonsten gibt es "unschöne Effeckte, wenn das Geld schon gebucht ist, aber noch nicht angekommen ist. 
       if (diff > 49.0 || diff < -49 )
       {
           LOG.warn("!!!Amount is " + diff);
           LOG.warn("!!!This is to big. Therefore it will be not inserted");
           return;
       }
       
       if (diff >= 0.01 || diff <= -0.01 )
       {
           insertTransaction(diff,company);
       }
      
   }
    
   private void insertTransaction(Double wert,String company)
   {
       Transaction trans = new Transaction();
         Date date = Calendar.getInstance().getTime(); 
        // String uri = "http://localhost:8092/transaction";
         
         wert = Math.round(100.0 * wert) / 100.0;
         UriComponents uriComponents = UriComponentsBuilder.newInstance()
                 .scheme("http").host(host).port(port).path("/transaction").build();
        String uri=uriComponents.toUriString();
         RestTemplate restTemplate = new RestTemplate();
      
       trans.setName("Ertrag");
       
       trans.setBeschreibung("");
       trans.setCycle(0);
       
       trans.setKonto_id(budget.getKontoByName(company,host,port));
       trans.setKor_id(0);
       trans.setPartner(company);
       trans.setPlaned("N");
       trans.setDatum(new SimpleDateFormat("yyyy-MM-dd").format(date));
       trans.setWert(wert);
       trans.setKategorie(42);
       LOG.info("Insert Transaction! " + trans.getDatum());
       restTemplate.postForEntity(uri,trans, Transaction.class);
       
       
   }
}
