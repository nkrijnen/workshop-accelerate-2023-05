package eu.luminis.workshop.smallsteps.api

import eu.luminis.workshop.smallsteps.logic.domainService.auth.AccessDeniedException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

// Understands how to translate exceptions to http responses
fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            println("[WARN] $cause")
            call.respondText(
                cause.message ?: "Malformed request",
                status = HttpStatusCode.BadRequest
            )
        }
        exception<IllegalStateException> { call, cause ->
            println("[WARN] $cause")
            call.respondText(
                cause.message ?: "Conflict",
                status = HttpStatusCode.Conflict
            )
        }
        exception<AccessDeniedException> { call, cause ->
            println("[WARN] $cause")
            call.respondText(
                cause.message ?: "Access Denied",
                status = HttpStatusCode.Forbidden
            )
        }
        exception<Throwable> { call, cause ->
            println("[ERROR] $cause")
            call.respondText(
                text = "500: $cause",
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}