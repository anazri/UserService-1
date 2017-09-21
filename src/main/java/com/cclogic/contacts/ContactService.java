package com.cclogic.contacts;

import com.cclogic.exceptions.UnAuthorizedAccessException;
import com.cclogic.security.TokenAuthenticationService;
import com.cclogic.user.UserHeaderTokenData;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static com.cclogic.security.TokenAuthenticationService.HEADER_STRING;

/**
 * Created by Nishant on 9/20/2017.
 */

@Service
public class ContactService {

    private static final String DOMAIN = "http://192.168.3.58:8080";
    private static final String CREATE_CONTACT_URL = DOMAIN + "/user/addContact";
    private static final String GET_CONTACTS_URL = DOMAIN + "/user/Contacts";
    private static final String CONTACTS_API_TEST_URL = DOMAIN + "/test/Contact";


    public String testContactsAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(CONTACTS_API_TEST_URL, String.class);

        HttpStatus statusCode = responseEntity.getStatusCode();

        String result;

        if (statusCode == HttpStatus.OK || statusCode == HttpStatus.ACCEPTED) {
            result = responseEntity.getBody();
            return result;
        } else {
            System.out.println("Error : " + statusCode);
            System.out.println("Response : " + responseEntity);
            throw new UnAuthorizedAccessException("Can't create contact.");
        }
    }

    public String createContact(Contact contact, HttpHeaders headers, int Id) {

        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        UserHeaderTokenData userHeaderTokenData = TokenAuthenticationService.getUserData(headers.getFirst(HEADER_STRING));

        if (userHeaderTokenData.getId() != Id) {
            throw new UnAuthorizedAccessException("Cannot add contact to other's Id. Please use your own Id");
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("name", contact.getName());
        map.put("phoneNumber", contact.getPhoneNumber());

        Gson gson = new Gson();
        String jsonData = gson.toJson(map);

        HttpEntity<?> entity = new HttpEntity<>(jsonData, headers);

        RestTemplate restTemplate = new RestTemplate();

        System.out.println("Headers : " + headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(CREATE_CONTACT_URL, HttpMethod.POST, entity, String.class);

        HttpStatus statusCode = responseEntity.getStatusCode();

        String result;

        if (statusCode == HttpStatus.OK || statusCode == HttpStatus.ACCEPTED) {
            result = responseEntity.getBody();
            return result;
        } else {
            System.out.println("Error : " + statusCode);
            System.out.println("Response : " + responseEntity);
            throw new UnAuthorizedAccessException("Can't create contact.");
        }
    }

    public String getContacts(HttpHeaders headers) {
        RestTemplate restTemplate = new RestTemplate();


        HttpEntity<?> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(GET_CONTACTS_URL, HttpMethod.GET, entity, String.class);


        HttpStatus statusCode = response.getStatusCode();
        String result;

        if (statusCode == HttpStatus.OK || statusCode == HttpStatus.ACCEPTED) {
            result = response.getBody();
            return result;
        } else {
            System.out.println("Error : " + statusCode);
            System.out.println("Response : " + response);
            throw new UnAuthorizedAccessException("Can't get data from contacts API.");
        }

    }

}
