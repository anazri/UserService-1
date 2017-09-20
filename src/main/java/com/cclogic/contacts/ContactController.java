package com.cclogic.contacts;

import com.cclogic.user.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Nishant on 9/20/2017.
 */

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/users/contacts/test")
    public String test(){
        String response = contactService.testContactsAPI();
        return response;
    }

    @RequestMapping(value = "/users/{Id}/contacts", method = RequestMethod.POST)
    public String createContact(@PathVariable int Id, @RequestHeader HttpHeaders headers, @RequestBody Contact contact){
        String result = contactService.createContact(contact, headers, Id);
        return new CustomResponse(result).getResponse();
    }

    @RequestMapping(value = "users/contacts", method = RequestMethod.GET)
    public String getContacts(@RequestHeader HttpHeaders headers){
        return contactService.getContacts(headers);
    }
}
