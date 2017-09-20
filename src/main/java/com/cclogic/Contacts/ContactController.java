package com.cclogic.Contacts;

import com.cclogic.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nishant on 9/20/2017.
 */

@RestController
public class ContactController {

    @Autowired
    private UserService userService;



}
