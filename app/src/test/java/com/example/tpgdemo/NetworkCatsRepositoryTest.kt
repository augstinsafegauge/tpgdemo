package com.example.tpgdemo

import com.example.tpgdemo.data.NetworkCatsPhotosRepository
import com.example.tpgdemo.fake.FakeCatsApiService
import com.example.tpgdemo.fake.FakeDataSource
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import retrofit2.Response

class NetworkCatsRepositoryTest {

    @Test
    fun networkCatsPhotosRepository_getPhotos_verifyPhotoList() =
        runTest {
            val repository = NetworkCatsPhotosRepository(
                catsApiService = FakeCatsApiService()
            )
            assertEquals(FakeDataSource.photosList, repository.getCatsPhotos())




}

    @Test
    fun networkCatsPhotosRepository_getPhoto_verifyPhoto()=
        runTest {
            val repository = NetworkCatsPhotosRepository(
                catsApiService = FakeCatsApiService()
            )
            assertEquals(FakeDataSource.photosList[0], repository.getCatPhoto("img1"))

        }

    @Test
    fun networkCatsPhotosRepository_deletePhoto_verifyPhotoDeleted()=
    runTest {
        val repository = NetworkCatsPhotosRepository(
            catsApiService = FakeCatsApiService()
        )

      val response =     repository.deletePhoto("img1")
        assertEquals(Response.success(Unit).code(), response.code())


    }

    @Test
    fun networkCatsPhotosRepository_addPhoto_verifyPhotoAdded()=
        runTest {
            val repository = NetworkCatsPhotosRepository(

                catsApiService =  FakeCatsApiService())

         val response =   repository.addPhoto(
               MultipartBody.Part.createFormData("file", "cat_image.jpg")
            )
            assertNotNull( response.id )

        }
}