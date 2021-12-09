package de.wi2020sebgroup1.cinema.usermanagement;

import java.util.Optional;

import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.UserAlreadyExistsException;

public interface UserServiceInterface {
	
	User registerNewUserAccount(UserToBeRegistered user) throws UserAlreadyExistsException;

    User registerNewAdminUserAccount(UserToBeRegistered user) throws UserAlreadyExistsException;

    Optional<User> findUserByEmail(String userEmail);

    void saveRegisteredUser(User user);
    
}
