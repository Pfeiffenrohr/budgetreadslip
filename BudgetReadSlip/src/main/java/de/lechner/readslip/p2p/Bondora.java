package de.lechner.readslip.p2p;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.lechner.readslip.bon.SlipEntry;
import de.lechner.readslip.p2p.bondora.BondoraEntity;
import de.lechner.readslip.p2p.bondora.GoGrowAccounts;
import de.lechner.readslip.parser.Transaction;

@Service
public class Bondora {
    
    @Autowired
    P2PService p2pService;
    
   
    public void callBondora() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        System.out.println("Call Bondora " + formatter.format(cal.getTime()));
        RestTemplate restTemplate = new RestTemplate();
        String host = "api.bondora.com";
        String path = "api/v1/account/balance";
        String accessToken = "fn5RU0Fyd06JuirfYsxE2LgpnN9HUuKhjlSkwBLMxxI2abMk";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(host)
                .path(path).build();
        String url = uriComponents.toUriString();
        // System.out.println("Uri from Bpondora: "+url);
         HttpEntity<String> entity = new HttpEntity<String>(headers);
         HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
         
         
         String amount = response.getBody();
        System.out.println("Gesamtreponse>"+amount+"<");
         amount= amount.substring(amount.indexOf("NetProfit") +11);       
         amount = amount.substring(0, amount.indexOf(","));
         System.out.println("responseBondora >" + amount );
         
         SlipEntry se = new SlipEntry();
         se.setName("Gesamtertrag");
         se.setSum(amount);
         
         List <SlipEntry> list = new ArrayList<SlipEntry>();
         list.add(se);
         
         p2pService.parseP2P(list, "Bondora");
         
         /*   HttpEntity<BondoraEntity> response = restTemplate.exchange(url, HttpMethod.GET, entity, BondoraEntity.class);
         
     
         
         BondoraEntity bondora =response.getBody();
         if (response.hasBody())
         {
             System.out.println("Has Body");
         }
         if (bondora.getSuccess())
         {
             System.out.println("success");
         }
         System.out.println(bondora.getErrors());
         */    
       //  List <GoGrowAccounts> list = new ArrayList<GoGrowAccounts>();
       //  list = bondora.getPayload().getGoGrowAccounts();
         
       //  System.out.println("Result from Bondora: "+ list.get(0).getNetProfit());
         
        //ResponseEntity<Transaction[]> response = restTemplate.getForEntity(uriString, Transaction[].class);
        // ResponseEntity<Bon> response =
        // restTemplate.getForEntity("http://localhost:8092/bon_by_rawname/Leerd",
        // Bon.class);

        
    }

}


