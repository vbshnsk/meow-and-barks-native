package mvvm.validators

class UsernameValidator {
    companion object {
        fun validate(username: String) = username.matches(
                Regex("^[a-z0-9_]{3,16}$"))
    }
}