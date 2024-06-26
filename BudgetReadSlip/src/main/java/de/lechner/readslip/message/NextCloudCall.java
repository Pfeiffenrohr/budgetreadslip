package de.lechner.readslip.message;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;



public class NextCloudCall {
    private static final Logger LOG = LoggerFactory.getLogger(NextCloudCall.class);
    public void sendMessageToTalk(String msg) {
        RestTemplate restTemplate = new RestTemplate();
        LOG.info("Start sendmessage to talk");
        String plainCreds = "richard:Thierham123";
        byte[] encodedAuth = Base64.encodeBase64(
                plainCreds.getBytes(Charset.forName("US-ASCII")), false);
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        headers.add("OCS-APIRequest", "true");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("token", "wz6hggy5");
        map.add("message", msg);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("richardlechner.spdns.de")
                .path("/ocs/v2.php/apps/spreed/api/v1/chat/kzuuotjy").build();
        String url = uriComponents.toUriString();
        ResponseEntity<String> response = restTemplate.postForEntity(
                url, request, String.class);
        String result = response.getBody();
    }
}
