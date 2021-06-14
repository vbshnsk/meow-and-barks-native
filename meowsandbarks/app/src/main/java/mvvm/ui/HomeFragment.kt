package mvvm.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.meowsandbarks.R
import mvvm.viewmodel.CurrentUserViewModel

class HomeFragment : Fragment() {

    val userInfo: CurrentUserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater.inflate(R.layout.simple_home, container, false)

        val usernameText = v.findViewById<TextView>(R.id.username_text)
        val emailText = v.findViewById<TextView>(R.id.email_text)

        userInfo.user.observe(viewLifecycleOwner, Observer {
            usernameText.text = it.username
            emailText.text = it.email
        })

        val logoutButton = v.findViewById<Button>(R.id.logout_button)

        logoutButton.setOnClickListener {
            val prefs = requireActivity()
                .getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)

            prefs.edit()
                .remove("token")
                .apply()

            requireActivity().finish()
        }

        if (!userInfo.userExists()) {
            userInfo.tryRequestUser()
        }

        return v
    }
}