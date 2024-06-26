package de.lechner.readslip.parser;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.lechner.readslip.bon.Bon;
import de.lechner.readslip.bon.SlipEntry;
import de.lechner.readslip.bon.SlipEntryList;
import de.lechner.readslip.message.Budget;


@Service
@Component
public class ParseSlip {
	@Value("${budgetserver.host}")
	private String host;
	@Value("${budgetserver.port}")
	private String port;
	@Autowired
	Budget budget;
	

	public void analyse(String text,String company) {
	
		List <String >splited  = scanner (text);
		List<SlipEntry> list =parser(splited);
		ausgabe(list);
		//getVategorieByName("Käse");
		checkBon(list,company);	
		
	}	
	

	public void analyseRest(SlipEntryList listSE,String company) {
		List <SlipEntry>  list = listSE.getList();
		
		checkBon(list,company);
		
		
	}
	/**
	 * Geht den ganzen Text durch, holt die notwemdigen Zeilen raus und gibt sie als
	 * Liste von Strings zurück
	 * @param text Rohtext der Datei
	 * @return Liste von Zeilen
	 */
	public List <String> scanner (String text) {
		List  <String> list = new  ArrayList<String>();
		String splited [] = text.trim().split("\n");
		
		for (int i=0; i< splited.length; i++)
		{
			if (! splited[i].contains("€") && ! splited[i].contains("{"))//TODO workaround
			{
				continue;
			}
			
			list.add(splited[i]);
		}
		return list;
		
	}
	/**
	 * Geht die Zeilen der Liste durch und macht eine Liste von SLipEntrys daraus
	 * @param txt Liste mit Zeilen  
	 * @return Liste von SlipEntrys
	 */
	public List  <SlipEntry>  parser (List <String> txt)
	{
		  System.out.println("Starte Parser...");
		List  <SlipEntry> list = new  ArrayList<SlipEntry>();
		boolean valueFound=false;
		for (int i=0; i< txt.size();i++)
		{
			String [] splited =txt.get(i).trim().split("\\s+");
		    SlipEntry slen = new SlipEntry();
		    slen.setSum(splited[splited.length-2]);
		    String name="";
		    for (int j=0; j<splited.length-2; j++)
		    {
		    	name=name+" "+splited[j];
		    }
		    System.out.println(splited[0]);
		    if (splited[0].length()==1 && splited[1].length()==1)
		    {
		    	continue;
		    }
		    if (splited[0].trim().startsWith("-"))
		    {
		    	continue;
		    }
		    		    
		    if (splited.length>1 && splited[1].trim().equals("x"))
		    {
		    	continue;
		    } 
		    System.out.println("Splitted " + splited[1]);
		    if ( splited.length>2 && splited[1].equals("Rabatt"))
		    {
		    	name="Coupon";
		    }
		   
		    slen.setName(name.trim());
		    list.add(slen);
		}
		//Ausgabe
	return list;
	}
	 
	private void checkBon( List  <SlipEntry> list,String company ) {
		RestTemplate restTemplate = new RestTemplate();
		Bon bon = new Bon();
		for (SlipEntry slipEntry : list) {
			bon.setId(0);
			bon.setCompany(company);
			//Falls im Name ein Rabatt vorkommt, setze den Namen auf Rabatt
			if (slipEntry.getName().contains("Coupon")) {
				slipEntry.setName("Rabatt");
			}
			bon.setRawname(slipEntry.getName());
			bon.setInternalname("unknown");
			bon.setRawnameMutant(slipEntry.getName());
			String ppp = slipEntry.getName();
			String ggg = URLEncoder.encode(ppp);

			UriComponents uriComponents = UriComponentsBuilder.newInstance()
					.scheme("http").host(host).port(port).path("/bon_by_rawname/" + ggg).build();

			String bonByRenameurl = uriComponents.toUriString().replace("+", "%20");
			System.out.println(bonByRenameurl);

			ResponseEntity<Bon> response = restTemplate.getForEntity(bonByRenameurl, Bon.class);
			//ResponseEntity<Bon> response = restTemplate.getForEntity("http://localhost:8092/bon_by_rawname/Leerd", Bon.class);


			if (response.hasBody()) {
				bon = response.getBody();
				assert bon != null;
				System.out.println(bon.getInternalname());

				if (bon.getInternalname().equals("unknown")) {
					insertTransaction(slipEntry, "unknown", false, company);// unknown
				} else {
					insertTransaction(slipEntry, bon.getInternalname(), true, company); // full
				}

			} else {
				//String bonUrl = "http://localhost:8092/bon";
				uriComponents = UriComponentsBuilder.newInstance()
						.scheme("http").host(host).port(port).path("/bon").build();
				String bonUrl = uriComponents.toUriString();
				System.out.println("Bon not found!! ");
				System.out.println("Insert Bon " + bon.getRawnameMutant());
				restTemplate.postForEntity(bonUrl, bon, Bon.class);
				insertTransaction(slipEntry, "unknown", false, company); // unknown
			}
		}
	}
	
	private void insertTransaction(SlipEntry slen,String name,boolean foundName,String company)
	{
		Transaction trans = new Transaction();
		  Date date = Calendar.getInstance().getTime(); 
		 // String uri = "http://localhost:8092/transaction";
		  UriComponents uriComponents = UriComponentsBuilder.newInstance()
			      .scheme("http").host(host).port(port).path("/transaction").build();
		 String uri=uriComponents.toUriString();
		  RestTemplate restTemplate = new RestTemplate();
		if  (foundName)
		{
			trans.setName(name);
		}
		else
		{
		trans.setName(slen.getName());
		}
		trans.setBeschreibung("");
		trans.setCycle(0);
		trans.setKonto_id(9);//TODO hardcoded Konto
		trans.setKor_id(0);
		trans.setPartner(company);
		trans.setPlaned("N");
		trans.setDatum(new SimpleDateFormat("yyyy-MM-dd").format(date));
		trans.setWert(new Double(slen.getSum().replace(',', '.'))*-1);
		if (! foundName)
		{
			trans.setKategorie(-1);
		}
		else
		{
			trans.setKategorie(new Integer (budget.getKategorieByName(name,host,port)));
		}
		System.out.println("Insert Transaction! " + trans.getDatum());
		restTemplate.postForEntity(uri,trans, Transaction.class);
		
		
	}
	

	
	private void ausgabe(List<SlipEntry> list ) {
		System.out.println("Ausgabe");
		for (int i=0; i< list.size();i++)
		{
			SlipEntry slen = list.get(i);
		}
	}
	
	
	
	
	/**
	 * Prüft, ob es sich wirklich um eine Summe handelt.
	 * 
	 */
	private boolean isSumme(String str ) {
		if (! str.contains(",") )
		{
			return false;
		}
		//Prüfe auf Nachkommastellen
		String [] parts = str.split(",");
		if ( parts[parts.length-1].length() != 2 )
		{
			return false;
		}
		if (Pattern.matches("[a-zA-Z]+", str) == true)
		{
			return false;
		}
		return true;
	}
}
