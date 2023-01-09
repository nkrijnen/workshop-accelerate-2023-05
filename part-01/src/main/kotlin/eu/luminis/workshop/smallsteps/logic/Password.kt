package eu.luminis.workshop.smallsteps.logic

data class Password(val value: String) {
    init {
        require(value.isNotBlank() && value.length >= 8) {
            "Password must be at least 8 characters long"
        }
    }
}