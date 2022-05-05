package eu.luminis.workshop.smallsteps.logic

import eu.luminis.workshop.smallsteps.persistence.UserDAO

// Understands how users can be registered
class UserService(private val userPersistence: UserDAO) {
    suspend fun registerNewUser(command: NewUserCommand) {
        require(command.email.isNotBlank()) { "Please enter an email address" }
        require(command.password.isNotBlank() && command.password.length >= 8) { "Password must be at least 8 characters long" }

        userPersistence.insertUser(command.email, command.password)
    }
}

data class NewUserCommand(
    val email: String,
    val password: String,
)