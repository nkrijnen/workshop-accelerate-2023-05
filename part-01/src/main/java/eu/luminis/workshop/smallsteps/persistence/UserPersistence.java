package eu.luminis.workshop.smallsteps.persistence;

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

    public UUID insertUser(String email, String password) {
        User newUser = new User(UUID.randomUUID(), email, password);
        users.add(newUser);
        return newUser.getId();
    }
}
