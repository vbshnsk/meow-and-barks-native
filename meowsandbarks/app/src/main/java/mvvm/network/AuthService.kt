package mvvm.network

import mvvm.data.*
import retrofit2.Call
import retrofit2.http.*

interface AuthService {

    @POST("/pets-auth/users/sign-in")
    fun signIn(@Body userRequest: LoginRequest): Call<LoginResponse>

    @POST("/pets-auth/users/sign-up")
    fun signUp(@Body userRequest: RegisterRequest): Call<Unit>

    @GET("/pets-auth/users/profile")
    fun getUser(
        @Header("Authorization") token: String): Call<ProfileResponse>

}