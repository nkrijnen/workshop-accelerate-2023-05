package eu.luminis.workshop.smallsteps.api;

import eu.luminis.workshop.smallsteps.logic.NewUserCommand;

public class NewUserRequest {
    private final String email;
    private final String password;

    public NewUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public NewUserCommand toCommand() {
        return new NewUserCommand(this.email, this.password);
    }
}
