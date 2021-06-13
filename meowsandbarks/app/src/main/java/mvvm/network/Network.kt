package mvvm.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {
    companion object {
        private val petsApi = Retrofit.Builder()
            .baseUrl("https://pets-api.phandev.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService: AuthService = petsApi.create(AuthService::class.java)
    }
}