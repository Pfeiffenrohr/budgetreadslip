package de.lechner.readslip.fonds;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.lechner.readslip.bon.BonController;
import de.lechner.readslip.message.NextCloudCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.lechner.readslip.bon.SlipEntry;
import de.lechner.readslip.bon.SlipEntryList;
import de.lechner.readslip.message.Budget;
import de.lechner.readslip.parser.Transaction;

@Service
public class FondsService {

    @Value("${budgetserver.host}")
    private String host;
    @Value("${budgetserver.port}")
    private String port;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    Budget budget;

   private static final Logger LOG = LoggerFactory.getLogger(FondsService.class);

    public void analyseRest(SlipEntryList listSE, String company) {
        List<SlipEntry> list = listSE.getList();

        parseFonds(list);
    }

    public Transaction parseFonds(List<SlipEntry> list) {
        Double diff = 0.0;
        //Vorerst haben wir nur Gesamtertrag. Daher ist die Liste evtl überflüssig
        //Aber vielleicht bekommen wir in Zukunft ja mehr
        if (list.size() < 1) {
            //Liste ist leer. kein richtiger Wert bekommen
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            String aktDepotSumme = list.get(i).getSum();
            String depotkonto = list.get(i).getName();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calnow = Calendar.getInstance();
            String enddate = formatter.format(calnow.getTime());
            //Hole erstmal den richtigen Kontonamen
            String realKontoName = budget.getInternalKontoname(depotkonto, host, port);
            LOG.info("Kontname = " + realKontoName);
            if (realKontoName.equals("-1")) {
                LOG.error("!! " + realKontoName + "Not found ");
                new NextCloudCall().sendMessageToTalk("@richard BudgetReadSlip Error: " + realKontoName + " not found");
                continue;
            }
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("http").host(host).port(port).path("/transaction_get_sum")
                    .queryParam("startdate", "2011-01-01")
                    .queryParam("enddate", enddate)
                    .queryParam("konto", budget.getKontoByName(realKontoName, host, port))
                    .build();


            String uri = uriComponents.toUriString();
            //System.out.println(uri);
            String result = restTemplate.getForObject(uri, String.class);
            if (result == null || result.equals("")) {
                System.out.println("No result found. Set result to 0");
                result = "0.0";
            }
            LOG.info("Found " + result + " amount now");


            //Extrawurst für viainvest

            diff = new Double(aktDepotSumme) - new Double(result);
            LOG.info("Found to insert " + diff);
            //Falls der Betrag größer 49€ oder kleiner -49 € ist, dann wird er vorerst nicht eingetragen
            // Ansonsten gibt es "unschöne Effeckte, wenn das Geld schon gebucht ist, aber noch nicht angekommen ist.

            if (diff > 0.001 || diff < -0.001) {
                return insertTransaction(diff, realKontoName);
            }
        }
        return null;
    }

    private Transaction insertTransaction(Double wert, String realKontoName) {
        Transaction trans = new Transaction();
        Date date = Calendar.getInstance().getTime();
        // String uri = "http://localhost:8092/transaction";

        wert = Math.round(100.0 * wert) / 100.0;
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path("/transaction").build();
        String uri = uriComponents.toUriString();
        trans.setName("Ertrag");

        trans.setBeschreibung("");
        trans.setCycle(0);
        LOG.info("RealKonotname " + realKontoName);
        trans.setKonto_id(budget.getKontoByName(realKontoName, host, port));
        trans.setKor_id(0);
        trans.setPartner(realKontoName);
        trans.setPlaned("N");
        trans.setDatum(new SimpleDateFormat("yyyy-MM-dd").format(date));
        trans.setWert(wert);
        trans.setKategorie(42);
        LOG.info("Insert Transaction! " + trans.getDatum());
        restTemplate.postForEntity(uri, trans, Transaction.class);
        return trans;

    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
