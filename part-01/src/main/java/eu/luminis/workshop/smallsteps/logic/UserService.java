package eu.luminis.workshop.smallsteps.logic;

import eu.luminis.workshop.smallsteps.api.NewUserRequest;
import eu.luminis.workshop.smallsteps.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
    @Autowired
    UserPersistence userPersistence;

    public void registerNewUser(NewUserRequest request) {
        if (!StringUtils.hasText(request.getEmail())) {
            throw new IllegalArgumentException("Please enter an email address");
        }
        if (!StringUtils.hasText(request.getPassword()) || request.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        userPersistence.insertUser(request);
    }
}
