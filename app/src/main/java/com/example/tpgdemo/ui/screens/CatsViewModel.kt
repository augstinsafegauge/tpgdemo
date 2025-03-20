package com.example.tpgdemo.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tpgdemo.CatsPhotosApplication
import com.example.tpgdemo.data.CatsPhotosRepository
import com.example.tpgdemo.model.CatsPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.io.OutputStream
import kotlin.random.Random
/**
 * UI state for the Home screen
 */
sealed interface CatsUiState {
    data class Success(val photos: List<CatsPhoto>) : CatsUiState
    object Error : CatsUiState
    object Loading : CatsUiState
}

class CatsViewModel(private val catsPhotosRepository: CatsPhotosRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var catsUiState: CatsUiState by mutableStateOf(CatsUiState.Loading)
    private val _isUploading = MutableStateFlow(false) // Backing field
    val isUploading: StateFlow<Boolean> = _isUploading // Public state
    /**
     * Gets Cats photos information from the Cats API Retrofit service and updates the
     * [CatsPhoto] [List] [MutableList].
     */
    fun getCatsPhotos() {
        viewModelScope.launch {
            catsUiState = CatsUiState.Loading
            catsUiState = try {

                CatsUiState.Success(
                    catsPhotosRepository.getCatsPhotos()
                )
            } catch (e: IOException) {
                CatsUiState.Error
            } catch (e: HttpException) {
                CatsUiState.Error
            }
        }
}
    fun getRandomCatsPhotos() {
        viewModelScope.launch {
            catsUiState = CatsUiState.Loading
            catsUiState = try {

                CatsUiState.Success(
                    catsPhotosRepository.getRandomPhotos()
                )
            } catch (e: IOException) {
                CatsUiState.Error
            } catch (e: HttpException) {
                CatsUiState.Error
            }
        }

    }

 suspend fun deleteCatsPhoto(imageId: String) {

     // First, delete from the repository
     catsPhotosRepository.deletePhoto(imageId)
     // Then, update the state to remove the deleted image
     val currentState = catsUiState
     if (currentState is CatsUiState.Success) {
         val updatedPhotos = currentState.photos.filterNot { it.id == imageId }
         catsUiState = CatsUiState.Success(updatedPhotos)
     }
    }
    fun uploadPhoto(context: Context) {

        // Calls suspend function inside coroutine
        viewModelScope.launch {
            _isUploading.value = true  // Update loading state
            uploadCatsPhoto(context)
            _isUploading.value = false // Reset after upload
        }

    }
    private fun saveDrawableToFile(context: Context, drawableResId: Int): File {
        val bitmap = BitmapFactory.decodeResource(context.resources, drawableResId)
        val file = File(context.cacheDir, "cat_image.jpg") // Create a temp file in cache

        // Ensure the file exists before writing
        file.outputStream().use {  outputStream : OutputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Compress as JPEG
        }
        return file
    }
    private suspend fun uploadCatsPhoto(context: Context) {
        try {
            //  Convert Drawable to File
            val randomCatNumber = Random.nextInt(1, 21)
            // upload random cat image from assets
          val resourceId =  context.resources.getIdentifier("cat$randomCatNumber", "drawable", context.packageName)
            val file = saveDrawableToFile(context,resourceId)
            //  Convert File to MultipartBody.Part
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            //  Upload via Retrofit
            val response = catsPhotosRepository.addPhoto(body)
            if (response.imageUrl.isNotEmpty()) {
                val uploadedPhoto = response// Get the uploaded photo details
                uploadedPhoto.let { newPhoto ->
                    val currentState = catsUiState
                    if (currentState is CatsUiState.Success) {
                        // Add New Photo to UI State
                        val updatedPhotos = currentState.photos.toMutableList()
                        updatedPhotos.add(CatsPhoto(newPhoto.id, newPhoto.imageUrl))
                        catsUiState = CatsUiState.Success(updatedPhotos)
                    }
                }
            }
        } catch (e: Exception) {
          //  Log.e("CatsViewModel", "Error uploading photo", e)

        }
    }

    suspend fun getCatPhoto(imageId: String): CatsPhoto {
        return catsPhotosRepository.getCatPhoto(imageId)
    }

    /**
     * Factory for [CatsViewModel] that takes [CatsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CatsPhotosApplication)
                val catsPhotosRepository = application.container.catsPhotosRepository
                CatsViewModel(catsPhotosRepository = catsPhotosRepository)
            }
        }
    }



}

