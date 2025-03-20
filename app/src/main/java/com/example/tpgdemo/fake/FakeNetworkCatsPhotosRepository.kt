package com.example.tpgdemo.fake

import com.example.tpgdemo.data.CatsPhotosRepository
import com.example.tpgdemo.model.CatsPhoto
import okhttp3.MultipartBody
import retrofit2.Response

class FakeNetworkCatsPhotosRepository : CatsPhotosRepository {


    override  suspend fun  getCatsPhotos(): List<CatsPhoto> {
        return FakeDataSource.photosList

    }

    override suspend fun getRandomPhotos(): List<CatsPhoto> {
        return FakeDataSource.photosList
    }

    override suspend fun getCatPhoto(imageId: String): CatsPhoto {
        return CatsPhoto(
            id = "img1",
            imageUrl = "url.one"
        )
    }

    override suspend fun deletePhoto(imageId: String): Response<Unit> {
        return Response.success(Unit)
    }

    override suspend fun addPhoto(photo: MultipartBody.Part): CatsPhoto {

        val newPhoto = CatsPhoto(
            id = "img3",
            imageUrl = "url.three"
        )
        return newPhoto
    }
}