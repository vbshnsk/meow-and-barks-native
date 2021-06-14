package mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mvvm.data.ProfileResponse
import mvvm.data.UserLiveData
import mvvm.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentUserViewModel: ViewModel() {

    val user: MutableLiveData<UserLiveData> = MutableLiveData()
    private val token: MutableLiveData<String> = MutableLiveData()

    fun setToken(token: String) {
        this.token.value = token
    }

    fun tryRequestUser() {
        Log.i("test", "token " + token.value)
        Network.authService.getUser("Bearer " + token.value!!)
            .enqueue(object : Callback<ProfileResponse> {
                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        val b = response.body()!!.user
                        user.value = UserLiveData(b.username, "", b.email)
                    }
                }

            })


    }

    fun userExists(): Boolean {
        return user.value != null
    }

}