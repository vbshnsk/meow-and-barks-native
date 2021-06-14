package mvvm.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meowsandbarks.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_nav)
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()

        val preferences =
            getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
        if (preferences.contains("token")) {
            val goToProfile = Intent(this, MainActivity::class.java)
            startActivity(goToProfile)
        }
    }
}