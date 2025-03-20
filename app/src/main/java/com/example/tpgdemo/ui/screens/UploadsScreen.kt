package com.example.tpgdemo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun UploadsScreen(
    catsViewModel: CatsViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // Call API only once when Composable launches
    LaunchedEffect(Unit) {
        catsViewModel.getCatsPhotos()
    }
    when (val catsUiState = catsViewModel.catsUiState) {
        is CatsUiState.Loading ->

            Column (modifier = modifier
                .fillMaxSize()
                .padding(top = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp), // Space between button & grid
                horizontalAlignment = Alignment.CenterHorizontally){
                AddPhotoButton(catsViewModel)

                LoadingScreen(modifier = modifier.fillMaxSize())
            }
        is CatsUiState.Success ->

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp), // Space between button & grid
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Add Photo Button at the top
                AddPhotoButton(catsViewModel)

                // Photo Grid below
                PhotosGridScreen(
                    photos = catsUiState.photos,
                    catsViewModel = catsViewModel,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = contentPadding
                )
            }
        is CatsUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}


@Composable
fun AddPhotoButton(
    viewModel: CatsViewModel,
) {
    val context = LocalContext.current // Get Context
    val isUploading by viewModel.isUploading.collectAsState() // Observe state changes
    Button(onClick = { viewModel.uploadPhoto(context) },enabled = !isUploading ) {
        if(isUploading) Text("Uploading...")
        else Text("Add Photo")
    }
}

