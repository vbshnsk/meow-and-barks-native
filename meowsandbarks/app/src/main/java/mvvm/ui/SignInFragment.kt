package mvvm.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.meowsandbarks.R
import com.google.android.gms.common.util.SharedPreferencesUtils
import mvvm.viewmodel.UserLoginFormViewModel
import util.setupValueSetterInForm

class SignInFragment : Fragment() {

    private val form: UserLoginFormViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_login, container, false)
        view.findViewById<Button>(R.id.register_button).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
        }
        view.findViewById<Button>(R.id.login_button).setOnClickListener {
            if (form.isLoginDataFullyFilled()) {
                form.tryLogin()
            }
        }

        form.token.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                showErrorMessage()
            }
            else {
                val prefs = requireActivity()
                    .getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
                with(prefs.edit()) {
                    putString("token", it)
                    apply()
                }
                findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
            }
        })

        val emailField = view.findViewById<EditText>(R.id.login_form_email)
        emailField.setupValueSetterInForm("Invalid email") {
            form.setEmail(it)
        }

        val passwordField = view.findViewById<EditText>(R.id.login_form_password)
        passwordField.setupValueSetterInForm("Invalid password") {
            form.setPassword(it)
        }

        return view
    }

    private fun showErrorMessage() {
        Log.i("test", "show error message")
        // TODO
    }

}