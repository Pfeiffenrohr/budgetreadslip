package de.lechner.readslip.transaction;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.lechner.readslip.parser.Transaction;
import de.lechner.readslip.slip.Bon;

@Service
public class UpdateTransactions {

	public void update() {

		List<Transaction> list = readUnupdatedTransactions();
		updateTransactions(list);

	}

	private List<Transaction> readUnupdatedTransactions() {

		List<Transaction> list = new ArrayList<Transaction>();
		RestTemplate restTemplate = new RestTemplate();
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(8080)
				.path("/transaction_by_kategorie/-1").build();
		String bonByRenameurl = uriComponents.toUriString();
		System.out.println(bonByRenameurl);
		ResponseEntity<Transaction[]> response = restTemplate.getForEntity(bonByRenameurl, Transaction[].class);
		// ResponseEntity<Bon> response =
		// restTemplate.getForEntity("http://localhost:8080/bon_by_rawname/Leerd",
		// Bon.class);

		if (response.hasBody()) {

			Transaction[] transactions = response.getBody();
			for (int i = 0; i < transactions.length; i++) {
				list.add(transactions[i]);
			}
		}
		return list;
	}

	private void updateTransactions(List<Transaction> list) {
		Bon bon = new Bon();
		RestTemplate restTemplate = new RestTemplate();
		for (int i = 0; i < list.size(); i++) {
			Transaction trans = list.get(i);
			String name = trans.getName();
			// schau nun, ob es den Namen schon gibt
			String ggg=  URLEncoder.encode(name, StandardCharsets.UTF_8);
			UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(8080)
					.path("/bon_by_rawname/" + ggg).build();

			String bonByRenameurl = uriComponents.toUriString().replace("+", "%20");
			System.out.println(bonByRenameurl);

			ResponseEntity<Bon> response = restTemplate.getForEntity(bonByRenameurl, Bon.class);
			// ResponseEntity<Bon> response =
			// restTemplate.getForEntity("http://localhost:8080/bon_by_rawname/Leerd",
			// Bon.class);

			if (response.hasBody()) {
				bon = response.getBody();
				System.out.println("Found !");
				if (!bon.getInternalname().equals("unknown")) {
					System.out.println("Name = "+bon.getInternalname());
					// Update nun die Transaktion
					trans.setName(bon.getInternalname());
					trans.setKategorie(new Integer(getKategorieByName(bon.getInternalname())));
					//String id = trans.getId().toString();
					System.out.println("Kategorie = "+trans.getKategorie());
					UriComponents uri = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(8080)
							.path("/transaction").build();

					String transByRenameurl = uri.toUriString().replace("+", "%20");
					
					System.out.println("Transactionurl =" + transByRenameurl);
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity<Transaction> entity = new HttpEntity<Transaction>(trans, headers);
					ResponseEntity<String> transresponse = restTemplate.exchange(transByRenameurl, HttpMethod.PUT,
							entity, String.class, trans.getId());

				}

			}
		}
	}
	private static String getKategorieByName(String name)
	{
		String ggg=  URLEncoder.encode(name, StandardCharsets.UTF_8);
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(8080)
				.path("/transaction_get_kategorie_byname/" + ggg).build();

		String bonByRenameurl = uriComponents.toUriString().replace("+", "%20");
        System.out.println(bonByRenameurl);   
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(bonByRenameurl, String.class);
	    if (result==null || result.equals(""))
	    {
	    	return "-1";
	    }
	    String res [] =result.split(",");
	    String kat=res[1];
	    System.out.println("Kategorie = " + kat);
	    return kat;
	}
}
