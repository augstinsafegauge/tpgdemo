package com.example.tpgdemo.data
import com.example.tpgdemo.model.CatsPhoto
import com.example.tpgdemo.network.CatsApiService
import okhttp3.MultipartBody
import retrofit2.Response


interface CatsPhotosRepository {

    /** Fetches list of CatPhoto from catApi*/
    suspend fun getCatsPhotos(): List<CatsPhoto>
    suspend fun getCatPhoto(imageId: String): CatsPhoto
    suspend fun getRandomPhotos(): List<CatsPhoto>
    suspend fun deletePhoto(imageId: String): Response<Unit>
    suspend fun addPhoto(photo: MultipartBody.Part): CatsPhoto
}

/**
 * Network Implementation of Repository that fetch cats photos list from catsApi.
 */
class NetworkCatsPhotosRepository(
    private val catsApiService: CatsApiService
) : CatsPhotosRepository{
    /** Fetches list of CatPhoto from catApi*/
    override suspend fun  getCatsPhotos(): List<CatsPhoto> = catsApiService.getPhotos()
    override suspend fun getRandomPhotos(): List<CatsPhoto> = catsApiService.getRandomPhotos()
    override suspend fun getCatPhoto(imageId: String): CatsPhoto = catsApiService.getPhoto(imageId)
    override suspend fun addPhoto(photo: MultipartBody.Part): CatsPhoto = catsApiService.addPhoto(photo)
    override suspend fun deletePhoto(imageId: String): Response<Unit> = catsApiService.deletePhoto(imageId)
}