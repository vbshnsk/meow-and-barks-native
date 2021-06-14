package mvvm.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.meowsandbarks.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import mvvm.viewmodel.PetViewModel
import mvvm.viewmodel.UserLoginFormViewModel
import util.setupValueSetterInForm

class AddPetBottomSheetFragment: BottomSheetDialogFragment() {
    private val pet: PetViewModel by viewModels()
    private val form: UserLoginFormViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.add_pet_dialog, container, false)
        pet.reset()

        val nameForm = view.findViewById<TextInputEditText>(R.id.add_pet_name)
        nameForm.setupValueSetterInForm("Invalid name") {
            pet.setName(it)
            true
        }

        val ageForm = view.findViewById<TextInputEditText>(R.id.add_pet_age)
        ageForm.setupValueSetterInForm("Invalid age") {
            try {
                pet.setAge(it.toInt())
            }
            catch (e: Exception) {
            }
            true
        }

        val typeForm = view.findViewById<TextInputEditText>(R.id.add_pet_age)
        typeForm.setupValueSetterInForm("Invalid type") {
            pet.setType(it)
            true
        }

        val breedForm = view.findViewById<TextInputEditText>(R.id.add_pet_age)
        breedForm.setupValueSetterInForm("Invalid breed") {
            pet.setType(it)
            true
        }

        setupImageAdd(view)

        view.findViewById<Button>(R.id.add_pet).setOnClickListener {
            if (pet.isValidPet()) {
                form.addPet(pet.pet.value!!)
                dismiss()
            }
        }

        return view
    }

    private fun setupImageAdd(view: View) {
        val imageAdd = view.findViewById<MaterialCardView>(R.id.add_card_button)
        val fakeBtn = view.findViewById<Button>(R.id.add_photo_fake_button)
        pet.currentImageUri.observe(viewLifecycleOwner, Observer {
            Log.i("test", "observed")
            imageAdd.removeAllViews()
            if (it != null) {
                fakeBtn.visibility = INVISIBLE
                val v = layoutInflater.inflate(R.layout.image_base, null)
                v.findViewById<ImageView>(R.id.image)
                    .setImageURI(it)
                imageAdd.addView(v)
            }
            else {
                fakeBtn.visibility = VISIBLE
            }
        })
        imageAdd.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) ==
                PERMISSION_GRANTED) {
                trySetImage()
            }
            else {

                Dexter.withContext(context)
                    .withPermission(READ_EXTERNAL_STORAGE)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                            trySetImage()
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            request: PermissionRequest,
                            token: PermissionToken
                        ) {
                            token.continuePermissionRequest()
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) {
                            Log.i("test", "perm denied")
                        }

                    }).check()
            }
        }
    }

    private fun trySetImage() {
        Log.i("test", "setting")
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 1111)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("test", "$requestCode $resultCode")
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1111 -> {
                    val image = data?.data
                    pet.setImage(MediaStore.Images.Media.getBitmap(activity?.contentResolver, image), image!!)
                }
            }
        }
    }
}