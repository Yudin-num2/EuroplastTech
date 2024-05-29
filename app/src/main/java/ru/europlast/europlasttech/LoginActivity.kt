package ru.europlast.europlasttech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.europlast.europlasttech.ui.theme.EvpCyan
import ru.europlast.europlasttech.ui.theme.ThumbChecked
import ru.europlast.europlasttech.ui.theme.ThumbUnchecked
import ru.europlast.europlasttech.ui.theme.TrackChecked
import ru.europlast.europlasttech.ui.theme.TrackUnchecked
import ru.europlast.europlasttech.ui.theme.WhiteTransparent

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
    Box(
        modifier = Modifier.fillMaxSize(),
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

            val login = remember{
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
                    value = login.value,
                    onValueChange = { login.value = it },
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .background(WhiteTransparent, shape = RoundedCornerShape(4.dp))
                        .border(2.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            val password = remember {
                mutableStateOf("")
            }
            Column {
                Text(
                    text = stringResource(id = R.string.password_label),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 25.dp)

                    )
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    textStyle = TextStyle(color = Color.Black),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .background(WhiteTransparent, shape = RoundedCornerShape(4.dp))
                        .border(2.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            val isRememberMeClicked = remember { mutableStateOf(false) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(

                    checked = isRememberMeClicked.value,
                    onCheckedChange = {
                        isRememberMeClicked.value = !isRememberMeClicked.value},
                    modifier = Modifier.padding(start = 20.dp),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = ThumbChecked,
                        checkedTrackColor = TrackChecked,
                        uncheckedThumbColor = ThumbUnchecked,
                        uncheckedTrackColor = TrackUnchecked,
                    )


                )

                Text(
                    text = stringResource(id = R.string.remember_me_label),
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(80.dp))

            Button(onClick = { //Надо отправлять запрос на веб-сервер REST API,
                             // но сначала валидация и шифрование //
                             navController.navigate(Screens.MainScreen.route){
                                 popUpTo("login") { inclusive = true }
                             }
            },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(EvpCyan)
            ) {
                Text(text = stringResource(R.string.auth_button_text),
                    style = TextStyle(color = Color.White,
                        fontSize = 24.sp),
                    textAlign = TextAlign.Center
               )

                }
            }

        }

    }


