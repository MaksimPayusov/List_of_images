package com.example.list_of_images

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
// import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
// import com.bumptech.glide.integration.compose.GlideImage
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.Image
import coil.compose.rememberAsyncImagePainter

val DarkColors = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = DarkColors
            ) {
                GalleryApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryApp() {
    val focusManager = LocalFocusManager.current

    var searchText by remember { mutableStateOf("") }
    var isGridView by remember { mutableStateOf(false) }
    var gallery by remember { mutableStateOf(ImagesCollections.generateSamplePictures()) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    placeholder = { Text("Search by name...") },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { isGridView = !isGridView },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            if (isGridView) {
                                Icons.AutoMirrored.Filled.List
                            } else Icons.Default.Menu,
                            contentDescription = "Switch the view"
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            val newPicture = ImagesCollections.generateNewPicture()
                            if (gallery.none { it.id == newPicture.id || it.url == newPicture.url }) {
                                gallery = gallery + newPicture
                            }
                        },
                        containerColor = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
                        modifier = Modifier.size(46.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add an image")
                    }

                    Button(
                        onClick = { gallery = emptyList() }
                    ) {
                        Text("Delete all")
                    }
                }
            }
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    val newPicture = ImagesCollections.generateNewPicture()
//                    if (gallery.none { it.id == newPicture.id || it.url == newPicture.url }) {
//                        gallery = gallery + newPicture
//                    }
//                },
//                containerColor = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "Add an image")
//            }
//        }
    ) { padding ->
        val filteredGallery = gallery.filter {
            it.author.contains(searchText, ignoreCase = true)
        }

        if (isGridView) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(filteredGallery) { picture ->
                    PictureItem(
                        picture = picture,
                        onPictureClick = { gallery = gallery - picture }
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(filteredGallery) { picture ->
                    PictureItem(
                        picture = picture,
                        onPictureClick = { gallery = gallery - picture }
                    )
                }
            }
        }
    }
}

@Composable
fun PictureItem(picture: Picture, onPictureClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onPictureClick)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = picture.url),
                contentDescription = "Image from ${picture.author}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = picture.author,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}