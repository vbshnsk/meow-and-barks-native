package mvvm.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meowsandbarks.R

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.register_button).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
        }
        view.findViewById<Button>(R.id.login_button).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
        }
    }
}