package mvvm.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PetsService {

    @POST("/pets-service/pets")
    fun addPet(
        @Header("Authorization") token: String,
        @Body name: String,
        @Body age: Number,
        @Body kind: String
    ): Call<Unit>

    @POST("/pets-service/pets/upload-photo")
    fun uploadPhoto(
        @Header("Authorization") token: String
    ): Call<Unit>

}