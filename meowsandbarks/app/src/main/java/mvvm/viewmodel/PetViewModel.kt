package mvvm.viewmodel

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import mvvm.data.*
import mvvm.network.Network
import mvvm.validators.EmailValidator
import mvvm.validators.PasswordValidator
import mvvm.validators.UsernameValidator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import util.notifyObserver
import java.util.*

class PetViewModel : ViewModel() {

    var pet: MutableLiveData<PetLiveData> = MutableLiveData(PetLiveData())
    var currentImageUri: MutableLiveData<Uri> = MutableLiveData()

    fun reset() {
        pet = MutableLiveData(PetLiveData())
        currentImageUri = MutableLiveData()
    }

    fun setImage(image: Bitmap, uri: Uri) {
        pet.value?.image = image
        currentImageUri.value = uri
        Log.i("test", "set image")
    }

    fun setName(name: String) {
        pet.value?.name = name
    }

    fun setAge(age: Number) {
        pet.value?.age = age
    }

    fun setType(type: String) {
        pet.value?.type = type
    }

    fun setBreed(breed: String) {
        pet.value?.breed = breed
    }

    fun isValidPet(): Boolean {
        return !pet.value?.name.isNullOrEmpty() &&
                !(pet.value?.age === null) &&
                !(pet.value?.image === null) &&
                !pet.value?.type.isNullOrEmpty()
    }

}