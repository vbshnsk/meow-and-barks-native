package mvvm.validators

import android.util.Patterns

class EmailValidator {
    companion object {
        fun validate(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}