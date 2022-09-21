package eu.luminis.workshop.smallsteps.logic;

public class NewUserCommand {
    private final String email;
    private final String password;

    public NewUserCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
