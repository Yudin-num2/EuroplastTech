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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtn
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtnBorder

class AddAnomalyActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnomalyScreen(navController: NavController) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    var showCameraPermissionDeniedSnackbar by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var anomalyName by rememberSaveable { mutableStateOf(("")) }
    var machineName by rememberSaveable { mutableStateOf("") }
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
                if (bitmap != null) {
                    selectedImageBitmap = bitmap
                }
            }
        }
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(cameraIntent)
        } else {
            showCameraPermissionDeniedSnackbar = true
        }
    }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.padding(paddingValues)
        ) {
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
                    .clickable {
                        navController.navigate(Screens.MainScreen.route) {
                            popUpTo("add_anomaly_screen") { inclusive = true }
                        }
                    }
                    .zIndex(2f),
            )
            Card(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .height(300.dp)
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
                border = BorderStroke(1.dp, color = Color.LightGray)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageBitmap != null) {
                        Image(
                            bitmap = selectedImageBitmap!!.asImageBitmap(),
                            contentDescription = "Selected photo",
                            modifier = Modifier
                                .size(250.dp)
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
                                },
                            contentScale = ContentScale.Fit
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
            Column(
                modifier = Modifier
                    .padding(top = 400.dp)
                    .verticalScroll(rememberScrollState(), enabled = true)
            ) {
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
                    border = BorderStroke(1.dp, color = Color.LightGray)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            modifier = Modifier
                                .zIndex(1f)
                                .fillMaxWidth(),
                            label = {
                                Text(
                                    text = stringResource(R.string.defect_title_label),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                            },
                            value = anomalyName,
                            onValueChange = { anomalyName = it },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(top = 15.dp))
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
                    border = BorderStroke(1.dp, color = Color.LightGray)
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    val machines = listOf("Option 1", "Option 2", "Option 3") //TODO MACHINES
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = machineName,
                            onValueChange = { machineName = it },
                            label = {
                                Text(text = stringResource(R.string.enter_machine),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    ))
                            },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            machines.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        machineName = selectionOption
                                        expanded = false
                                    },
                                    text = { Text(selectionOption) }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(top = 15.dp))
                var anomalyComment by remember{ mutableStateOf("") }
                Card(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
                    border = BorderStroke(1.dp, color = Color.LightGray)
                ) {
                    TextField(
                        modifier = Modifier.fillMaxSize(),
                        value = anomalyComment,
                        onValueChange = {anomalyComment = it},
                        placeholder = { Text(text = "Описание аномалии")}
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(onClick = {
                    navController.navigate(Screens.MainScreen.route){
                        popUpTo("add_anomaly_screen") { inclusive = true }}
                }
                                 /*TODO Write on server and navController.navigate*/ ,
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .zIndex(1f)
                        .border(
                            2.dp, color = SaveAndExitBtnBorder,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(SaveAndExitBtn)
                ){
                    Text(text = stringResource(R.string.save_and_exit),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center)
                    )

                }




            }
        }

    }

    LaunchedEffect(showCameraPermissionDeniedSnackbar) {
        if (showCameraPermissionDeniedSnackbar) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.snackbar_dont_get_permisson_camera),
                duration = SnackbarDuration.Long,
                actionLabel = "OK"
            )
            showCameraPermissionDeniedSnackbar = false
        }
    }

    LaunchedEffect(selectedImageUri) {
        if (selectedImageUri == null && ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
}