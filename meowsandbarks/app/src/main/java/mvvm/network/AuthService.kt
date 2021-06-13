package mvvm.network

import mvvm.data.LoginRequest
import mvvm.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/pets-auth/users/sign-in")
    fun signUp(@Body userRequest: LoginRequest): Call<LoginResponse>

}