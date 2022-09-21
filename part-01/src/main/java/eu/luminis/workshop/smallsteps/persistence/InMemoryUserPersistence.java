package eu.luminis.workshop.smallsteps.persistence;

import eu.luminis.workshop.smallsteps.logic.UserPersistence;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class InMemoryUserPersistence implements UserPersistence {
    private final Set<User> users;

    public InMemoryUserPersistence() {
        users = new HashSet<>();
    }

    public UUID insertUser(String email, String password) {
        User newUser = new User(UUID.randomUUID(), email, password);
        users.add(newUser);
        return newUser.getId();
    }
}
