package eu.luminis.workshop.smallsteps.logic

data class Email(val value: String) {
    init {
        require(value.isNotBlank()) {
            "Please enter an email address"
        }
    }
}