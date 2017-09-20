package com.cclogic.Security;

/**
 * Created by Nishant on 9/19/2017.
 */

import com.cclogic.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.cclogic.User.User user = userRepository.findByEmailId(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getEmailId(), user.getPassword(), emptyList());
    }
}
