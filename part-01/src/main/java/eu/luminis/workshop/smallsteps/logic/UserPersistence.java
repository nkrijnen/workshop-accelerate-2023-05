package eu.luminis.workshop.smallsteps.logic;

import java.util.UUID;

public interface UserPersistence {
    UUID insertUser(String email, String password);
}
