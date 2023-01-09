package eu.luminis.workshop.smallsteps.logic

import java.util.*

// Understands how users can be registered
class UserService(private val userPersistence: UserPersistence) {
    suspend fun registerNewUser(command: NewUserCommand) {
        userPersistence.insertUser(command.email, command.password)
    }
}

data class NewUserCommand(
    val email: Email,
    val password: Password,
)

interface UserPersistence {
    suspend fun insertUser(email: Email, password: Password): UUID
}