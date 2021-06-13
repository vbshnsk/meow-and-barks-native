package mvvm.validators

class PasswordValidator {
    companion object {
        fun validate(password: String) = password.matches(
                Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
        )
    }
}