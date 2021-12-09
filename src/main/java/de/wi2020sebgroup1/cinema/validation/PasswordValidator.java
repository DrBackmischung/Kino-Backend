package de.wi2020sebgroup1.cinema.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.wi2020sebgroup1.cinema.usermanagement.UserToBeRegistered;

public class PasswordValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
    	UserToBeRegistered user = (UserToBeRegistered) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
