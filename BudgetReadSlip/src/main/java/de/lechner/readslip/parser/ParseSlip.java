package de.lechner.readslip.parser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.lechner.readslip.slip.Bon;

@Service
public class ParseSlip {
	
	public void analyse(String text) {
		
		List <String >splited  = scanner (text);
		List list =parser(splited);
		ausgabe(list);
		getVategorieByName("Käse");
		checkBon(list);
		
		
	}
	
	public List <String> scanner (String text) {
		List  <String> list = new  ArrayList<String>();
		String splited [] = text.trim().split("\n");
		
		for (int i=0; i< splited.length; i++)
		{
			if (! splited[i].contains("€"))
			{
				continue;
			}
			list.add(splited[i]);
			System.out.println(">"+splited[i] +"<" );
		}
		return list;
		
	}
	
	public List  <SlipEntry>  parser (List <String> txt)
	{
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
		    if (splited[0].length()==1 && splited[1].length()==1)
		    {
		    	continue;
		    }
		    slen.setName(name);
		    list.add(slen);
		}
		//Ausgabe
	return list;
	}
	
	private void checkBon( List  <SlipEntry> list ) {
		RestTemplate restTemplate = new RestTemplate();
		Bon bon = new Bon();
		for (int i = 0; i < list.size(); i++) {
			bon.setCompany("Netto");
			bon.setRawname(list.get(i).getName());
			bon.setInternalname("unknown");
			bon.setRawnameMutant(list.get(i).getName());
			String ppp=list.get(i).getName();
			String ggg=  URLEncoder.encode(ppp, StandardCharsets.UTF_8);
			UriComponents uriComponents = UriComponentsBuilder.newInstance()
				      .scheme("http").host("localhost").port(8080).path("/bon_by_rawname/"+ggg).build();
			System.out.println(uriComponents.toUriString());
			String bonByRenameurl=uriComponents.toUriString();

			ResponseEntity<Bon> response = restTemplate.getForEntity(bonByRenameurl, Bon.class);
			if (response.hasBody()) {
				bon = response.getBody();
				System.out.println(bon.getInternalname());

				if (bon.getInternalname().equals("unknown")) {
					insertTransaction(list.get(i), "unknown");// unknown
				} else {
					insertTransaction(list.get(i), bon.getInternalname()); // full
				}

			} else {
				String bonUrl = "http://localhost:8080/bon";

				System.out.println("bon not found!!");
				restTemplate.postForEntity(bonUrl, bon, Bon.class);
				insertTransaction(list.get(i), "unknown"); // unknown
			}
		}
	}
	
	private void insertTransaction(SlipEntry slen,String name)
	{
		Transaction trans = new Transaction();
		  Date date = Calendar.getInstance().getTime(); 
		  String uri = "http://localhost:8080/transaction";
		  RestTemplate restTemplate = new RestTemplate();
		trans.setName(slen.getName());
		trans.setBeschreibung("");
		trans.setCycle(0);
		trans.setKonto_id(9);//TODO hardcoded Konto
		trans.setKor_id(0);
		trans.setPartner("");
		trans.setPlaned("N");
		trans.setDatum(new SimpleDateFormat("yyyy-MM-dd").format(date));
		trans.setWert(new Double(slen.getSum().replace(',', '.')));
		if (name.equals("unknown"))
		{
			trans.setKategorie(-1);
		}
		else
		{
			trans.setKategorie(new Integer (getVategorieByName("name")));
		}
		System.out.println("Insert Transaction!");
		restTemplate.postForEntity(uri,trans, Transaction.class);
		
		
	}
	
	private static String getVategorieByName(String name)
	{
	    final String uri = "http://localhost:8080/transaction_get_kategorie_byname/"+name;

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    System.out.println(result);
	    return result;
	}
	
	
	private void ausgabe(List<SlipEntry> list ) {
		System.out.println("Ausgabe");
		for (int i=0; i< list.size();i++)
		{
			SlipEntry slen = list.get(i);
			System.out.println("Name:" + slen.getName()+" Summe: " +slen.getSum() );
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
