package com.cclogic.Contacts;

import com.cclogic.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Nishant on 9/20/2017.
 */

@Service
public class ContactService {

    @Autowired
    private UserRepository userRepository;

    public void createContact(Contact contact){



    }
}
