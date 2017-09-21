package com.cclogic.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @Before
    public void setup(){
        user = new User("9191919191","user95","user@gmail.com","password","user");
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void findByEmailId(){
        User currentUser = userRepository.findByEmailId("user@gmail.com").get(0);

        assertTrue(currentUser.getEmailId().equals(user.getEmailId()));
    }

    @Test
    public void findByPhoneNumber(){
        User currentUser = userRepository.findByPhoneNumber("9191919191").get(0);

        assertTrue(currentUser.getPhoneNumber().equals(user.getPhoneNumber()));
    }

    @Test
    public void findByUserName(){
        User currentUser = userRepository.findByUserName("user95").get(0);

        assertTrue(currentUser.getUserName().equals(user.getUserName()));
    }

    @Test
    public void findByUserType(){
        User currentUser = userRepository.findByUserType("user").get(0);

        assertTrue(currentUser.getUserType().equals(user.getUserType()));
    }

}