package com.example.tpgdemo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tpgdemo.R
import com.example.tpgdemo.ui.screens.CatsViewModel
import com.example.tpgdemo.ui.screens.PublicScreen
import com.example.tpgdemo.ui.screens.UploadsScreen
import com.example.tpgdemo.ui.screens.WelcomeScreen


enum class CatsScreen() {
    Welcome,
    Public,
    Uploads
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsPhotosApp(
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = CatsScreen.Welcome.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Welcome Screen
            composable(route = CatsScreen.Welcome.name) {
              Column {
                  CatsTopAppBar(
                      canNavigateBack = navController.previousBackStackEntry != null,  // Now always shows back button on Gallery
                      navigateUp = { navController.navigateUp() },
                      scrollBehavior = scrollBehavior
                  )
                  WelcomeScreen(
                      modifier = Modifier.fillMaxSize(),
                      onPublicClick = {
                          navController.navigate(CatsScreen.Public.name)
                      },
                      onUploadClick = {
                          navController.navigate(CatsScreen.Uploads.name)
                      }
                  )
              }
            }
            // Gallery Screen
            composable(route = CatsScreen.Public.name) {
                PublicScreenWrapper(navController = navController, scrollBehavior = scrollBehavior)
            }

            composable(route = CatsScreen.Uploads.name) {
                UploadsScreenWrapper(navController = navController, scrollBehavior = scrollBehavior )


            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicScreenWrapper(navController: NavHostController, scrollBehavior: TopAppBarScrollBehavior) {
    val catsViewModel: CatsViewModel = viewModel(factory = CatsViewModel.Factory)

    Column(modifier = Modifier.fillMaxSize()) {
        // Move TopAppBar inside GalleryScreen so it updates correctly
        CatsTopAppBar(
            canNavigateBack = navController.previousBackStackEntry != null,  // Now always shows back button on Gallery
            navigateUp = { navController.navigateUp() },
            scrollBehavior = scrollBehavior
        )

        Surface(
            modifier = Modifier.fillMaxHeight()
        ) {
            PublicScreen(
                retryAction = catsViewModel::getCatsPhotos,
                catsViewModel = catsViewModel
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadsScreenWrapper(navController: NavHostController, scrollBehavior: TopAppBarScrollBehavior) {
    val catsViewModel: CatsViewModel = viewModel(factory = CatsViewModel.Factory)

    Column(modifier = Modifier.fillMaxSize()) {
        // Move TopAppBar inside GalleryScreen so it updates correctly
        CatsTopAppBar(
            canNavigateBack = navController.previousBackStackEntry != null,  // Now always shows back button on Gallery
            navigateUp = { navController.navigateUp() },
            scrollBehavior = scrollBehavior
        )

        Surface(
            modifier = Modifier.fillMaxHeight()
        ) {
            UploadsScreen(

                catsViewModel = catsViewModel
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor =Color(0xFF800000)
        ),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.White
                    )
                }
            }
        },
        modifier = modifier
    )
}