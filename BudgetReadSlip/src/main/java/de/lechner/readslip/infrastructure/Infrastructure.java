package de.lechner.readslip.infrastructure;

import org.springframework.web.client.RestTemplate;

public class Infrastructure {

	
	public static String getKategorieByName(String name)
	{
	    final String uri = "http://localhost:8092/transaction_get_kategorie_byname/"+name;

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    if (result==null ||result.equals(""))
	    {
	    	return "-1";
	    }
	    String res [] =result.split(",");
	    String kat=res[1];
	    System.out.println("Kategorie = " + kat);
	    return kat;
	}
	
}
