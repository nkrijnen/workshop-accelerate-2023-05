package eu.luminis.workshop.smallsteps.api;

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
}
