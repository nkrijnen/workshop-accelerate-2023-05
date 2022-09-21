package eu.luminis.workshop.smallsteps.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
    @Autowired
    UserPersistence userPersistence;

    public void registerNewUser(NewUserCommand command) {
        if (!StringUtils.hasText(command.getEmail())) {
            throw new IllegalArgumentException("Please enter an email address");
        }
        if (!StringUtils.hasText(command.getPassword()) || command.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        userPersistence.insertUser(command.getEmail(), command.getPassword());
    }
}
