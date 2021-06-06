package mvvm.viewmodel

import androidx.lifecycle.*
import mvvm.data.BirthDate
import mvvm.data.CountryMap
import mvvm.data.UserLiveData
import util.notifyObserver
import java.util.*

class UserViewModel : ViewModel() {

    var userData: MutableLiveData<UserLiveData> = MutableLiveData(UserLiveData())
    var currentCountry: MutableLiveData<String> = MutableLiveData()

    fun addTag(tag: String) {
        userData.value?.profile?.tags?.add(tag)
        userData.notifyObserver()
    }

    fun setUsername(username: String) {
        userData.value?.username = username
    }

    fun setPassword(password: String) {
        userData.value?.password = password
    }

    fun setEmail(email: String) {
        userData.value?.email = email
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

    fun setBirthDate(date: Number, month: String, year: Number) {
        userData.value?.profile?.birthDate = BirthDate(date, month, year)
    }

}