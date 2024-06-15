package ru.europlast.europlasttech

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat

class AddDefectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddDefectScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri

                selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    uri
                )
            } ?: run {
                val bitmap = result.data?.extras?.get("data") as? Bitmap
                selectedImageBitmap = bitmap
            }
        }
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(cameraIntent)
        } else { //TODO добавь сюда Toast что пользователю необходимо дать разрешение в настройках ОС
            //TODO Также, глянь чтобы при нажатии на camera intent,
        }
    }
    Box(contentAlignment = Alignment.TopCenter) {
        Image(
            painter = painterResource(id = R.drawable.ic_background_img),
            contentDescription = "background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "return Icon",
            tint = Color.Black,
            modifier = Modifier
                .padding(top = 35.dp, start = 25.dp)
                .width(30.dp)
                .height(30.dp)
                .align(Alignment.TopStart)
                .clickable { /*navController.navigate(Screens.MainScreen.route){
                    popUpTo("current_tasks_screen") { inclusive = true }}*/
                }
                .zIndex(1f),
        )
        Card(
            modifier = Modifier
                .padding(top = 70.dp)
                .size(300.dp, 300.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageBitmap != null) {
                    Image(
                        bitmap = selectedImageBitmap!!.asImageBitmap(),
                        contentDescription = "Selected photo",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
                        contentDescription = "Add photo icon",
                        modifier = Modifier
                            .size(200.dp)
                            .clickable {
                                val galleryIntent = Intent(Intent.ACTION_PICK).apply {
                                    type = "image/*"
                                }
                                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                val chooserIntent = Intent
                                    .createChooser(
                                        galleryIntent,
                                        context.getString(
                                            R.string.get_image_from
                                        )
                                    )
                                    .apply {
                                        putExtra(
                                            Intent.EXTRA_INITIAL_INTENTS,
                                            arrayOf(cameraIntent)
                                        )
                                    }
                                launcher.launch(chooserIntent)
                            }
                    )
                }
            }
        }
    }

    LaunchedEffect(selectedImageUri) {
        if (selectedImageUri == null && ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
}