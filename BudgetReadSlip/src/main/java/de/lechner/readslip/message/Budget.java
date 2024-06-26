package de.lechner.readslip.message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class Budget {
    private static final Logger LOG = LoggerFactory.getLogger(Budget.class);

    public String getKategorieByName(String name, String host, String port) {
        //  final String uri = "http://localhost:8092/transaction_get_kategorie_byname/"+name;
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path("/transaction_get_kategorie_byname/" + name).build();
        String uri = uriComponents.toUriString();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        if (result == null || result.equals("")) {
            return "-1";
        }
        String[] res = result.split(",");
        return res[1];
    }

    public String getInternalKontoname(String name, String host, String port) {
        //  final String uri = "http://localhost:8092/transaction_get_kategorie_byname/"+name;
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path("/kontomatchByName/" + name).build();
        String uri = uriComponents.toUriString();
        LOG.info("getInternalKontoname URI = " + uri);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        LOG.info("getInternalKontoname Result =" + result);
        if (result == null || result.equals("")) {
            return "-1";
        }
        return result;
    }

    public Integer getKontoByName(String name, String host, String port) {
        //  final String uri = "http://localhost:8092/transaction_get_kategorie_byname/"+name;
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path("/kontoByName/" + name).build();
        String uri = uriComponents.toUriString();
        RestTemplate restTemplate = new RestTemplate();
        Integer result = restTemplate.getForObject(uri, Integer.class);
        if (result == null) {
            return -1;
        }

        return result;
    }

}
