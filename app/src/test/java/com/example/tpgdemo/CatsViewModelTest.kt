package com.example.tpgdemo

import com.example.tpgdemo.fake.FakeDataSource
import com.example.tpgdemo.fake.FakeNetworkCatsPhotosRepository
import com.example.tpgdemo.rules.TestDispatcherRule
import com.example.tpgdemo.ui.screens.CatsUiState
import com.example.tpgdemo.ui.screens.CatsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test



class CatsViewModelTest {
    /** Main dispatcher is only available in a UI context,
    replaced it with a unit-test dispatcher
    **/
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun catsViewModel_getCatsPhotos_verifyCatsPhotosUiStateSuccess() =
        runTest {
            val catsViewModel = CatsViewModel(
                catsPhotosRepository = FakeNetworkCatsPhotosRepository()
            )
            catsViewModel.getCatsPhotos()

            assertEquals(
                CatsUiState.Success(FakeDataSource.photosList),
                catsViewModel.catsUiState
            )
        }


    @Test
    fun catsViewModel_deletePhoto_verifyCatsPhotoDeleted() =
        runTest {
            // Arrange
            val fakeRepository = FakeNetworkCatsPhotosRepository()
            val catsViewModel = CatsViewModel(catsPhotosRepository = fakeRepository)

            // Act
            catsViewModel.deleteCatsPhoto("img1")

            // Assert: Check if UI state no longer contains "img1"
            if (catsViewModel.catsUiState is CatsUiState.Success) {
                val updatedPhotos = (catsViewModel.catsUiState as CatsUiState.Success).photos
                assertFalse(updatedPhotos.any { it.id == "img1" })


            }
        }



    @Test
    fun catsViewModel_getCatPhoto_verifyCatsPhotoRetrieved() = runTest {
        val fakeRepository = FakeNetworkCatsPhotosRepository()
        val catsViewModel = CatsViewModel(catsPhotosRepository = fakeRepository)
        val catPhoto = catsViewModel.getCatPhoto("img1")
        assertEquals("url.one", catPhoto.imageUrl)

    }

}