package com.example.tpgdemo

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tpgdemo.fake.FakeDataSource
import com.example.tpgdemo.fake.FakeNetworkCatsPhotosRepository
import com.example.tpgdemo.ui.screens.CatsUiState
import com.example.tpgdemo.ui.screens.CatsViewModel
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.tpgdemo", appContext.packageName)
  }

    @Test
    fun catsViewModel_addPhoto_verifyCatsPhotoAdded() = runTest {

        val context: Context =  InstrumentationRegistry.getInstrumentation().targetContext
        var lengthBefore = 0
        val fakeRepository = FakeNetworkCatsPhotosRepository()
        val catsViewModel = CatsViewModel(catsPhotosRepository = fakeRepository)

        // Get the initial number of photos


        catsViewModel.uploadPhoto(context)

        if (catsViewModel.catsUiState is CatsUiState.Success) {
            val updatedPhotos = (catsViewModel.catsUiState as CatsUiState.Success).photos
            assertEquals(FakeDataSource.photosList.size + 1, updatedPhotos.size)
  }

    }
}