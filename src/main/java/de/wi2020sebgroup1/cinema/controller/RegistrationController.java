package de.wi2020sebgroup1.cinema.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.usermanagement.UserServiceInterface;
import de.wi2020sebgroup1.cinema.usermanagement.UserToBeRegistered;

@RestController
public class RegistrationController {

    private final UserServiceInterface userService;
    private final UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    public RegistrationController(UserServiceInterface userService, UserRepository userRepository) {
        super();
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public ResponseEntity<?> showRegistrationForm() {
        UserToBeRegistered u = new UserToBeRegistered();

        return ResponseEntity.ok(u);
    }
    
    @GetMapping("/registration/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestBody User user) {

        userService.saveRegisteredUser(user);

        return ResponseEntity.accepted().body("Welcome, " + user.getFirstName() + "! Your account has been created.");
    }

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        if (getLoggedInUserName() != null && !getLoggedInUserName().isEmpty()) {
            return ResponseEntity.ok(userRepository.findByEmail(getLoggedInUserName()).orElseThrow(() -> new UsernameNotFoundException(getLoggedInUserName())).getRole());
        } else {
            return ResponseEntity.badRequest().body("Either the password or the email is incorrect.");
        }
    }

    private String getLoggedInUserName() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        } catch (NullPointerException e) {
            LOG.warn(e.getMessage());
        }

        return null;
    }
	
}
