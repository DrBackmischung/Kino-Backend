package de.wi2020sebgroup1.cinema.usermanagement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import de.wi2020sebgroup1.cinema.entities.Role;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.UserAlreadyExistsException;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

public class UserService implements UserServiceInterface {

    private final UserRepository repo;

    private final PasswordEncoder pe;
    
    public UserService(UserRepository repo, PasswordEncoder pe) {
    	this.repo = repo;
    	this.pe = pe;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserToBeRegistered user) throws UserAlreadyExistsException {
        checkIfEmailExists(user);
        return repo.save(setup(user, new Role("User role", "ROLE_USER")));
    }

    @Transactional
    @Override
    public User registerNewAdminUserAccount(UserToBeRegistered user) throws UserAlreadyExistsException {
        checkIfEmailExists(user);
        return repo.save(setup(user, new Role("Admin role", "ADMIN")));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public void saveRegisteredUser(User user) {
        repo.save(user);
    }

    private void checkIfEmailExists(UserToBeRegistered user) throws UserAlreadyExistsException{
        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    private boolean emailExists(String email) {
        return repo.findByEmail(email).isPresent();
    }

    private User setup(UserToBeRegistered u, Role role) {
        User user = new User();
        user.setFirstName(u.getFirstName());
        user.setName(u.getLastName());
        user.setPassword(pe.encode(u.getPassword()));
        user.setEmail(u.getEmail());
        user.setRole(role);

        return user;
    }

}
