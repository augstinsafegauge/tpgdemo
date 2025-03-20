package com.example.tpgdemo.network
import com.example.tpgdemo.model.CatsPhoto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface CatsApiService {
    @GET("images/?limit=10")
    suspend fun getPhotos(): List<CatsPhoto>

    @GET("images/search?limit=10")
    suspend fun getRandomPhotos(): List<CatsPhoto>

    @DELETE("images/{image_id}")
    suspend fun deletePhoto(@Path("image_id") imageId: String): Response<Unit>

    @GET("images/{image_id}")
    suspend fun getPhoto(@Path("image_id") imageId: String): CatsPhoto

    @Multipart
    @POST("images/upload")
    suspend fun addPhoto(
        @Part image: MultipartBody.Part
    ): CatsPhoto


}
