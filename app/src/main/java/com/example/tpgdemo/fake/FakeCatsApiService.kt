package com.example.tpgdemo.fake

import com.example.tpgdemo.model.CatsPhoto
import com.example.tpgdemo.network.CatsApiService
import okhttp3.MultipartBody
import retrofit2.Response

class FakeCatsApiService: CatsApiService {
    override suspend fun getPhotos(): List<CatsPhoto> {
        return FakeDataSource.photosList
    }


    override suspend fun deletePhoto(imageId: String): Response<Unit> {
        return  Response.success(Unit)

    }

    override suspend fun getPhoto(imageId: String): CatsPhoto {
       return CatsPhoto(
           id = "img1",
           imageUrl = "url.one"
       )
    }

    override suspend fun addPhoto(image: MultipartBody.Part): CatsPhoto {

        return  CatsPhoto(
            id = "img3",
            imageUrl = "url.three"
        )

    }

    override suspend fun getRandomPhotos(): List<CatsPhoto> {
        return FakeDataSource.photosList
    }





}