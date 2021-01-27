package de.lechner.readslip.parser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.lechner.readslip.bon.Bon;
import de.lechner.readslip.infrastructure.Infrastructure;


@Service
@Component
public class ParseSlip {
	@Value("${budgetserver.host}")
	private String host;
	@Value("${budgetserver.port}")
	private String port;
	

	public void analyse(String text,String company) {
		
		List <String >splited  = scanner (text);
		List list =parser(splited);
		ausgabe(list);
		//getVategorieByName("Käse");
		checkBon(list,company);
		
		
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
		    if (splited[0].trim().startsWith("-"))
		    {
		    	continue;
		    }
		    
		    if (splited.length>1 && splited[1].trim().equals("x"))
		    {
		    	continue;
		    } 
		    
		    if ( splited.length>2 && splited[2].equals("Rabatt"))
		    {
		    	name="Coupon";
		    }
		   
		    slen.setName(name);
		    list.add(slen);
		}
		//Ausgabe
	return list;
	}
	 
	private void checkBon( List  <SlipEntry> list,String company ) {
		RestTemplate restTemplate = new RestTemplate();
		
		Bon bon = new Bon();
		for (int i = 0; i < list.size(); i++) {
			bon.setId(0);
			bon.setCompany(company);
			bon.setRawname(list.get(i).getName());
			bon.setInternalname("unknown");
			bon.setRawnameMutant(list.get(i).getName());
			String ppp=list.get(i).getName();
			String ggg=  URLEncoder.encode(ppp, StandardCharsets.UTF_8);
			
			UriComponents uriComponents = UriComponentsBuilder.newInstance()
				      .scheme("http").host(host).port(port).path("/bon_by_rawname/"+ggg).build();
			
			String bonByRenameurl=uriComponents.toUriString().replace("+", "%20");
			System.out.println(bonByRenameurl);
       
			ResponseEntity<Bon> response = restTemplate.getForEntity(bonByRenameurl, Bon.class);
			//ResponseEntity<Bon> response = restTemplate.getForEntity("http://localhost:8092/bon_by_rawname/Leerd", Bon.class);
				  
			
			if (response.hasBody()) {
				bon = response.getBody();
				System.out.println(bon.getInternalname());

				if (bon.getInternalname().equals("unknown")) {
					insertTransaction(list.get(i), "unknown",false);// unknown
				} else {
					insertTransaction(list.get(i), bon.getInternalname(),true); // full
				}

			} else {
				//String bonUrl = "http://localhost:8092/bon";
				 uriComponents = UriComponentsBuilder.newInstance()
					      .scheme("http").host(host).port(port).path("/bon").build();
				 String bonUrl=uriComponents.toUriString();
				System.out.println("Bon not found!! ");
				System.out.println("Insert Bon "+bon.getRawnameMutant());
				restTemplate.postForEntity(bonUrl, bon, Bon.class);
				insertTransaction(list.get(i), "unknown",false); // unknown
			}
		}
	}
	
	private void insertTransaction(SlipEntry slen,String name,boolean foundName)
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
		trans.setPartner("Netto");
		trans.setPlaned("N");
		trans.setDatum(new SimpleDateFormat("yyyy-MM-dd").format(date));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
		trans.setWert(new Double(slen.getSum().replace(',', '.'))*-1);
		if (! foundName)
		{
			trans.setKategorie(-1);
		}
		else
		{
			trans.setKategorie(new Integer (Infrastructure.getKategorieByName(name,host,port)));
		}
		System.out.println("Insert Transaction! " + trans.getDatum());
		restTemplate.postForEntity(uri,trans, Transaction.class);
		
		
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
