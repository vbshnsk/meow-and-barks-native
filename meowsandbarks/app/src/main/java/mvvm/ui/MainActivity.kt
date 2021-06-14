package mvvm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import com.example.meowsandbarks.R
import mvvm.network.Network
import mvvm.viewmodel.CurrentUserViewModel

class MainActivity : AppCompatActivity() {

    val userInfo: CurrentUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_bar)
        supportActionBar?.hide()
        findViewById<Button>(R.id.nav_bar_map).setOnClickListener {
            findNavController(this, R.id.nav_empty)
                    .navigate(R.id.action_global_mapFragment)
        }
        findViewById<Button>(R.id.nav_bar_home).setOnClickListener {
            findNavController(this, R.id.nav_empty)
                    .navigate(R.id.action_global_homeFragment)
        }
        findViewById<Button>(R.id.nav_bar_heart).setOnClickListener {
            findNavController(this, R.id.nav_empty)
                    .navigate(R.id.action_global_notificationsFragment)
        }
        findViewById<Button>(R.id.nav_bar_user).setOnClickListener {
            findNavController(this, R.id.nav_empty)
                    .navigate(R.id.action_global_profileFragment)
        }

        val preferences =
            getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
        if (!preferences.contains("token")) {
            val goToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goToLogin)
        }

        val token = preferences.getString("token", null)
        userInfo.setToken(token!!)
    }

}