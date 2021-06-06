package mvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meowsandbarks.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_bar)
        supportActionBar?.hide()
    }
}