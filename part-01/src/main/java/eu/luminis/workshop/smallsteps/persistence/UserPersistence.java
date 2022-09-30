package eu.luminis.workshop.smallsteps.persistence;

import eu.luminis.workshop.smallsteps.api.NewUserRequest;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class UserPersistence {
    private final Set<User> users;

    public UserPersistence() {
        users = new HashSet<>();
    }

    public UUID insertUser(NewUserRequest request) {
        User newUser = new User(UUID.randomUUID(), request.getEmail(), request.getPassword());
        users.add(newUser);
        return newUser.getId();
    }
}
