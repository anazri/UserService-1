package com.cclogic.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nishant on 9/17/2017.
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByEmailId(String emailId);

}
