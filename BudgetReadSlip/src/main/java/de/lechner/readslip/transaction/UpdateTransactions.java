package de.lechner.readslip.transaction;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.lechner.readslip.bon.Bon;
import de.lechner.readslip.message.Budget;
import de.lechner.readslip.parser.Transaction;

@Service
public class UpdateTransactions {
	@Value("${budgetserver.host}")
	private String host;
	@Value("${budgetserver.port}")
	private String port;

	@Autowired
	Budget budget;

	public void update() {

		List<Transaction> list = readUnupdatedTransactions();
		updateTransactions(list);

	}

	private List<Transaction> readUnupdatedTransactions() {

		List<Transaction> list = new ArrayList<Transaction>();
		RestTemplate restTemplate = new RestTemplate();
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(host).port(port)
				.path("/transaction_by_kategorie/-1").build();
		String bonByRenameurl = uriComponents.toUriString();
		
		ResponseEntity<Transaction[]> response = restTemplate.getForEntity(bonByRenameurl, Transaction[].class);
		// ResponseEntity<Bon> response =
		// restTemplate.getForEntity("http://localhost:8092/bon_by_rawname/Leerd",
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
			String ggg=  URLEncoder.encode(name);
			UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(host).port(port)
					.path("/bon_by_rawname/" + ggg).build();

			String bonByRenameurl = uriComponents.toUriString().replace("+", "%20");
			System.out.println(bonByRenameurl);

			ResponseEntity<Bon> response = restTemplate.getForEntity(bonByRenameurl, Bon.class);
			// ResponseEntity<Bon> response =
			// restTemplate.getForEntity("http://localhost:8092/bon_by_rawname/Leerd",
			// Bon.class);

			if (response.hasBody()) {
				bon = response.getBody();
				if (!bon.getInternalname().equals("unknown")) {
					// Update nun die Transaktion
					trans.setName(bon.getInternalname());
					trans.setKategorie(new Integer(budget.getKategorieByName(bon.getInternalname(),host,port)));
					//String id = trans.getId().toString();
					UriComponents uri = UriComponentsBuilder.newInstance().scheme("http").host(host).port(port)
							.path("/transaction").build();

					String transByRenameurl = uri.toUriString().replace("+", "%20");
					
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity<Transaction> entity = new HttpEntity<Transaction>(trans, headers);
					ResponseEntity<String> transresponse = restTemplate.exchange(transByRenameurl, HttpMethod.PUT,
							entity, String.class, trans.getId());

				}

			}
		}
	}
	
}
