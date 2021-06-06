package mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.meowsandbarks.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import mvvm.viewmodel.UserViewModel

class AddPetBottomSheetFragment: BottomSheetDialogFragment() {
    private val form: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.add_pet_dialog, container, false)

        view.findViewById<Button>(R.id.add_pet).setOnClickListener {
            dismiss()
        }

        return view
    }
}