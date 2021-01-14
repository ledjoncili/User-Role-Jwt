package al.projekt.projekt.validators;

import al.projekt.projekt.annotations.UniqueUsername;
import al.projekt.projekt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userRepository == null) {
            return true;
        }
        return (userRepository.findByUsername(username) == null);
    }
}
