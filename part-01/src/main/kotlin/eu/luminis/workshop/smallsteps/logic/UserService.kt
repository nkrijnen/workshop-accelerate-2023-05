package eu.luminis.workshop.smallsteps.logic

import java.util.*

// Understands how users can be registered
class UserService(private val userPersistence: UserPersistence) {
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

interface UserPersistence {
    suspend fun insertUser(email: String, password: String): UUID
}