package de.wi2020sebgroup1.cinema.usermanagement;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.wi2020sebgroup1.cinema.validation.EmailValid;
import de.wi2020sebgroup1.cinema.validation.PasswordMatches;

@JsonSerialize
@PasswordMatches
public class UserToBeRegistered {

    @JsonSerialize
    @NotNull
    @NotEmpty
    private String firstName;

    @JsonSerialize
    @NotNull
    @NotEmpty
    private String lastName;

    @JsonSerialize
    @NotNull
    @NotEmpty
    private String password;

    @JsonSerialize
    private String matchingPassword;

    @JsonSerialize
    @EmailValid
    @NotNull
    @NotEmpty
    private String email;

    public UserToBeRegistered() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
