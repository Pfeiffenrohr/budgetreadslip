package de.lechner.readslip.message;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class Budget {
	public String getKategorieByName(String name,String host, String port)
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
	    String[] res =result.split(",");
		return res[1];
	}
	
	public String getInternalKontoname(String name,String host, String port)
    { 
      //  final String uri = "http://localhost:8092/transaction_get_kategorie_byname/"+name;
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                  .scheme("http").host(host).port(port).path("/kontomatchByName/"+name).build();
         String uri=uriComponents.toUriString();
         System.out.println("getInternalKontoname URI = "+uri);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println("getInternalKontoname Result ="+result);
        if (result==null ||result.equals(""))
        {
            return "-1";
        }
        return result;
    }
	
	public Integer getKontoByName(String name,String host, String port)
	{ 
	  //  final String uri = "http://localhost:8092/transaction_get_kategorie_byname/"+name;
	    UriComponents uriComponents = UriComponentsBuilder.newInstance()
			      .scheme("http").host(host).port(port).path("/kontoByName/"+name).build();
		 String uri=uriComponents.toUriString();
	    RestTemplate restTemplate = new RestTemplate();
	    //System.out.println("URI = "+uri);
	    Integer result = restTemplate.getForObject(uri, Integer.class);
	    //System.out.println("Result ="+result);
	    if (result == null)
	    {
	    	return -1;
	    }
	    
	    return result;
	}
	
}
