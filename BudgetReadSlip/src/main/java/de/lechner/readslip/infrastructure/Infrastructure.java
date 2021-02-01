package de.lechner.readslip.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


public class Infrastructure {
	
	
	public static String getKategorieByName(String name,String host, String port)
	{ 
	  //  final String uri = "http://localhost:8092/transaction_get_kategorie_byname/"+name;
	    UriComponents uriComponents = UriComponentsBuilder.newInstance()
			      .scheme("http").host(host).port(port).path("/transaction_get_kategorie_byname/"+name).build();
		 String uri=uriComponents.toUriString();
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    if (result==null ||result.equals(""))
	    {
	    	return "-1";
	    }
	    String res [] =result.split(",");
	    String kat=res[1];
	    return kat;
	}
	
}
