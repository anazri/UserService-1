package com.cclogic.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Nishant on 9/17/2017.
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByEmailId(String emailId);

}
