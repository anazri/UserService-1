package com.cclogic.User;

import com.cclogic.Exceptions.InvalidDataException;
import com.cclogic.Exceptions.ResourceNotFoundException;
import com.cclogic.Exceptions.UnAuthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nishant on 9/16/2017.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers() {
        List<User> users;
        users = userRepository.findAll();
        return users;
    }

    public User getUser(int Id) {
        User user = userRepository.findOne(Id);
        if(user == null) throw new ResourceNotFoundException("User with id : "+Id+" does not exists");
        return user;
    }

    public User getUserByEmail(String emailId){
        User user =  userRepository.findByEmailId(emailId);
        if(user == null) {throw new ResourceNotFoundException("User with Email Id : "+emailId+" does not exists");}
        return user;
    }

    public void addUser(OpenUser user) {
        User newUser = validateUser(user);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    public void updateUser(OpenUser user, int activeId, int accessId) {

        if(isNullOrEmpty(""+activeId) || activeId!=accessId){
            throw new UnAuthorizedAccessException("You are not authorized to update this account.");
        }

        User currentUser = userRepository.findOne(activeId);

        if(currentUser == null){
            throw new ResourceNotFoundException("Account not found. Probably your account is deleted!");
        }

        //Id can't be changed
        user.setId(currentUser.getId());

        //Email Id can't be changed
        user.setEmailId(currentUser.getEmailId());

        if(isNullOrEmpty(user.getPhoneNumber())){
            user.setPhoneNumber(currentUser.getPhoneNumber());
        }else if(user.getPhoneNumber().length()<10 || user.getPhoneNumber().length()>13){
            throw new InvalidDataException("Invalid Phone Number");
        }

        if(isNullOrEmpty(user.getUserName())){
            user.setUserName(currentUser.getUserName());
        }

        if(isNullOrEmpty(user.getPassword())){
            user.setPassword(currentUser.getPassword());
        }else if(user.getPassword().length()<6){
            throw new InvalidDataException("Invalid Password. Minimum Length Required : 6");
        }

        if(isNullOrEmpty(user.getUserType())){
            user.setUserType(currentUser.getUserType());
        }

        User newUser = getUserFromOpenUser(user);

        userRepository.save(newUser);
    }

    public void deleteUser(int Id, int activeId){

        User user = userRepository.findOne(Id);

        if(user.getId()!=activeId){
            throw new UnAuthorizedAccessException("You are not authorized to delete this account.");
        }

        userRepository.delete(Id);
    }


    private User validateUser(OpenUser user){

        String phoneNumber, userName, emailId, password, userType;
        phoneNumber = user.getPhoneNumber();
        userName = user.getUserName();
        emailId = user.getEmailId();
        password = user.getPassword();
        userType = user.getUserType();

        User dbUser = userRepository.findByEmailId(emailId);

        if(dbUser!=null){
            throw new InvalidDataException("Email id is already registered!");
        }else if(isNullOrEmpty(phoneNumber) || isNullOrEmpty(userName) || isNullOrEmpty(emailId) || isNullOrEmpty(password) || isNullOrEmpty(userType)){
            throw new InvalidDataException("All fields are required");
        }else if(!user.getEmailId().contains("@") || !user.getEmailId().contains(".")) {
            throw new InvalidDataException("Invalid EmailId");
        }else if(phoneNumber.length()<10 || phoneNumber.length()>13){
            throw new InvalidDataException("Invalid Phone Number");
        }else if(password.length()<6){
            throw new InvalidDataException("Invalid Password. Minimum Length Required : 6");
        }

        return getUserFromOpenUser(user);

    }

    private boolean isNullOrEmpty(String data){
        System.out.println("Data : "+data);
        return data==null || data.isEmpty();
    }

    private User getUserFromOpenUser(OpenUser user){
        User newUser = new User();
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setEmailId(user.getEmailId());
        newUser.setUserType(user.getUserType());

        return newUser;
    }
}
