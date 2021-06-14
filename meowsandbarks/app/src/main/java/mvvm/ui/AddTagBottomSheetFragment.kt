package mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.meowsandbarks.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import mvvm.viewmodel.UserLoginFormViewModel

class AddTagBottomSheetFragment : BottomSheetDialogFragment() {

    private val form: UserLoginFormViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.add_tag_dialog, container, false)

        view.findViewById<Button>(R.id.add_tag).setOnClickListener {
            val t = view.findViewById<TextInputEditText>(R.id.tag)
            t.text?.let {
                val text = it.trim().toString()
                if (text.isNotEmpty()) {
                    form.addTag(text)
                }
            }
            dismiss()
        }

        return view
    }

}