package mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.*
import mvvm.data.*
import mvvm.network.Network
import mvvm.validators.EmailValidator
import mvvm.validators.UsernameValidator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.notifyObserver

class UserLoginFormViewModel : ViewModel() {

    var userData: MutableLiveData<UserLiveData> = MutableLiveData(UserLiveData())
    var currentCountry: MutableLiveData<String> = MutableLiveData()
    var token: MutableLiveData<String> = MutableLiveData()
    var registerSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun addTag(tag: String) {
        if (userData.value?.profile?.tags?.none { it === tag }!!) {
            userData.value?.profile?.tags?.add(tag)
            userData.notifyObserver()
        }
    }

    fun removeTag(tag: String) {
        userData.value?.profile?.tags =
            userData.value?.profile?.tags?.filter { it !== tag }?.toMutableList()!!
        userData.notifyObserver()
    }

    fun tryLogin() {
        Network.authService.signIn(
            LoginRequest(userData.value!!.email!!, userData.value!!.password!!)
        ).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(res: Call<LoginResponse>, t: Throwable) {
                token.value = null
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.i("test", response.body()?.token.toString())
                    token.value = response.body()?.token
                }
                else {
                    token.value = null
                }
            }

        })
    }

    fun setUsername(username: String): Boolean {
        if (!UsernameValidator.validate(username)) {
            return false
        }
        userData.value?.username = username
        return true
    }

    fun setPassword(password: String): Boolean {
//        if (!PasswordValidator.validate(password)) {
//            return false
//        }
        userData.value?.password = password
        return true
    }

    fun setEmail(email: String): Boolean {
        if (!EmailValidator.validate(email)) {
            return false
        }
        userData.value?.email = email
        return true;
    }

    fun isUserDataFullyFilled(): Boolean {
        return !userData.value?.username.isNullOrEmpty() &&
                !userData.value?.email.isNullOrEmpty() &&
                !userData.value?.password.isNullOrEmpty()
    }

    fun setName(name: String) {
        userData.value?.profile?.name = name
    }

    fun setLocationCountry(country: String) {
        currentCountry.value = country
        userData.value?.profile?.location?.country = country
    }

    fun setLocationCity(city: String) {
        userData.value?.profile?.location?.city = city
    }

    fun setBirthDate(date: Number) {
        userData.value?.profile?.birthDate?.date = date
    }

    fun setBirthMonth(month: String) {
        userData.value?.profile?.birthDate?.month = month
    }

    fun setBirthYear(year: Number) {
        userData.value?.profile?.birthDate?.year = year
    }

    fun isLoginDataFullyFilled(): Boolean {
        return !userData.value?.email.isNullOrEmpty() &&
                !userData.value?.password.isNullOrEmpty()
    }

    fun addPet(pet: PetLiveData) {
        userData.value?.profile?.pets?.add(pet)
        userData.notifyObserver()
    }

    fun tryFinishProfile() {
        for (pet in userData.value?.profile!!.pets) {
            Network.petsService.addPet(token.value!!, pet.name!!, pet.age!!, pet.type!!)
                .enqueue(object : Callback<Unit> {
                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (pet.image != null) {
                            tryUploadPetImage(pet)
                        }
                    }

                })
        }
    }

    private fun tryUploadPetImage(pet: PetLiveData) {

    }

    fun tryRegister() {
        Network.authService.signUp(RegisterRequest(
            userData.value?.email!!,
            userData.value?.password!!,
            userData.value?.username!!
        )).enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        registerSuccess.value = true
                    }
                }
            }

        })
    }

}