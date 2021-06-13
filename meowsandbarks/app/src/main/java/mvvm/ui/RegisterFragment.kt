package mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.meowsandbarks.R
import mvvm.viewmodel.UserViewModel
import util.setupValueSetterInForm

class RegisterFragment : Fragment() {

    private val form: UserViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        view.findViewById<Button>(R.id.register_form_button).setOnClickListener {
            if (form.isUserDataFullyFilled()) {
                findNavController().navigate(R.id.action_registerFragment_to_createAccountFragment)
            }
        }

        val emailField = view.findViewById<EditText>(R.id.login_form_email)
        emailField.setupValueSetterInForm("Invalid email") {
            form.setEmail(it)
        }

        val nameField = view.findViewById<EditText>(R.id.login_form_name)
        nameField.setupValueSetterInForm("Invalid name") {
            form.setUsername(it)
        }

        val passwordField = view.findViewById<EditText>(R.id.login_form_password)
        passwordField.setupValueSetterInForm("Invalid password") {
            form.setPassword(it)
        }
        return view
    }


}