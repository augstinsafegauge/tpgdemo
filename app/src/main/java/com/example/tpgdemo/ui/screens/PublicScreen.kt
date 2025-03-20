package com.example.tpgdemo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil.request.ImageRequest
import com.example.tpgdemo.R
import com.example.tpgdemo.model.CatsPhoto
import kotlinx.coroutines.launch

@Composable
fun PublicScreen(
    retryAction: () -> Unit,
    catsViewModel: CatsViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // Call API only once when Composable launches
    LaunchedEffect(Unit) {
        catsViewModel.getRandomCatsPhotos()
    }
    when (val catsUiState = catsViewModel.catsUiState) {
        is CatsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CatsUiState.Success ->

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp), // Space between button & grid
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

/**
 *  Photo Card for Cats
 */

@Composable
fun CatsPhotoCard(
    photo: CatsPhoto,
    viewModel: CatsViewModel,

    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(photo.imageUrl)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.cats_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Delete Button (X Mark) at Top-Right Corner
        IconButton(
            onClick = {  viewModel.viewModelScope.launch {
                viewModel.deleteCatsPhoto(photo.id) // Call suspend function inside coroutine
            } }, // Pass the photo ID for deletion
            modifier = Modifier
                .align(Alignment.TopEnd) // Position at the top-right corner
                .padding(8.dp)
                .size(24.dp) // Size of the X button
                .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape) // Semi-transparent background
        ) {
            Icon(
                imageVector = Icons.Default.Close, // Default X icon
                contentDescription = stringResource(R.string.delete_photo),
                tint = Color.White
            )
        }
    }
}


/**
*  Displaying photo grid.
*/
@Composable
fun PhotosGridScreen(
    photos: List<CatsPhoto>,
    modifier: Modifier = Modifier,
    catsViewModel: CatsViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),

        modifier = modifier.padding(horizontal = 4.dp , vertical = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = photos, key = { photo -> photo.id }) { photo ->
            CatsPhotoCard(
                photo,
                catsViewModel,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}

