package mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meowsandbarks.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_nav)
        supportActionBar?.hide()
    }
}