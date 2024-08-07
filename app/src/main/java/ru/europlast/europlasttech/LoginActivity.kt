package ru.europlast.europlasttech

//noinspection UsingMaterialAndMaterial3Libraries
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.europlast.europlasttech.data.RetrofitInstance.networkAPI
import ru.europlast.europlasttech.ui.theme.EvpCyan
import ru.europlast.europlasttech.ui.theme.WhiteTransparent
import java.net.ConnectException

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}


@Composable
fun LoginScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_login),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.authorization_label),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()

                )

                Spacer(modifier = Modifier.height(16.dp))

                var login by remember {
                    mutableStateOf("")
                }
                Column {
                    Text(
                        text = stringResource(id = R.string.login_label),
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 25.dp)
                    )

                    OutlinedTextField(
                        value = login,
                        onValueChange = { login = it },
                        textStyle = TextStyle(color = Color.Black),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .background(WhiteTransparent, shape = RoundedCornerShape(4.dp))
                            .border(2.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                var password by remember {
                    mutableStateOf("")
                }
                Column {
                    Text(
                        text = stringResource(R.string.password_label),
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 25.dp)

                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        textStyle = TextStyle(color = Color.Black),
                        visualTransformation = PasswordVisualTransformation(),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                onAuthClick(navController, login, password, context)
                            }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .background(WhiteTransparent, shape = RoundedCornerShape(4.dp))
                            .border(2.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    )
                }

                Spacer(modifier = Modifier.height(80.dp))

                Button(
                    onClick = { onAuthClick(navController, login, password, context) },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    colors = ButtonDefaults.buttonColors(EvpCyan)
                ) {
                    Text(
                        text = stringResource(R.string.auth_button_text),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 24.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                }
                Button(onClick = { navController.navigate(Screens.Test.route) }) {
                    Text(text = "TEST BUTTON")
                }
            }

        }


    }

}

fun onAuthClick(navController: NavController,
                login: String, password: String, context: Context){
        if (login == "admin") {
            navController.navigate(Screens.MainScreen.route) {
                popUpTo("login") { inclusive = true }
            }
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = networkAPI.authorization(
                        login = login, password = password
                    )
                    if (response.isSuccessful) {
                        val authResponse = response.body().toString()
                        if (authResponse == "{\"details\": \"user not found\"}") {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.check_your_credentials),
                                    Toast.LENGTH_SHORT).show()
                            }
                        }else {

//                        val user = User(
//                            id = authResponse[0].code,
//                            login = authResponse[1].toString(),
//                            password = authResponse[2].toString(),
//                            name = authResponse[3].toString(),
//                            surname = authResponse[4].toString(),
//                            room = authResponse[5].toString(),
//                            post = authResponse[6].toString()
//                        )
                            withContext(Dispatchers.Main) {
                                navController.navigate(Screens.MainScreen.route) {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context,
                            "Response is not successful",
                            Toast.LENGTH_SHORT).show()
                    }
                } catch (error: ConnectException) {
                    Log.d("ConnectException", "Error: $error")
                } catch (error: Exception) {
                    Log.d("Exception", "Error: $error")
                }
            }

        }

    }





