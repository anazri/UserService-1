package com.cclogic.User;

import com.cclogic.Security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.cclogic.Security.TokenAuthenticationService.HEADER_STRING;

/**
 * Created by Nishant on 9/16/2017.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test(@RequestHeader HttpHeaders headers) {
        UserHeaderTokenData userHeaderTokenData = TokenAuthenticationService.getUserData(headers.getFirst(HEADER_STRING));
        System.out.println("Active Id : "+userHeaderTokenData.getId());
        return new CustomResponse("Test Successful!").getResponse();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/users/{Id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int Id) {
        return userService.getUser(Id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String addUser(@RequestBody OpenUser openUser) {
        userService.addUser(openUser);
        return new CustomResponse("User Registered Successfully!").getResponse();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{Id}")
    public String updateUser(@RequestBody OpenUser openUser, @PathVariable int Id, @RequestHeader HttpHeaders headers) {
        UserHeaderTokenData userHeaderTokenData = TokenAuthenticationService.getUserData(headers.getFirst(HEADER_STRING));
        userService.updateUser(openUser, userHeaderTokenData.getId(), Id);
        return new CustomResponse("User updated successfully!").getResponse();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{Id}")
    public String deleteUser(@PathVariable int Id, @RequestHeader HttpHeaders headers) {
        UserHeaderTokenData userHeaderTokenData = TokenAuthenticationService.getUserData(headers.getFirst(HEADER_STRING));
        userService.deleteUser(Id, userHeaderTokenData.getId());
        return new CustomResponse("User deleted successfully!").getResponse();
    }

    @RequestMapping("/user/getUserByEmail/{emailId:.+}")
    public User getUserByEmail(@PathVariable String emailId) {
        return userService.getUserByEmail(emailId);
    }

}
