package ru.europlast.europlasttech.sockets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.europlast.europlasttech.R


@Composable
fun ReasonsPopupForSockets(onDismiss: () -> Unit,
                           onColorSelected: (Color) -> Unit) {

    val listOfReasons = mutableListOf(
        R.string.good_socket to Color(0xFF37FF00),
        R.string.underfilling to Color(0xFF1C71D8),
        R.string.dirt to Color(0xFFAC7E64),
        R.string.needle_chipping to Color(0xFF7641D1),
        R.string.flash_by_closure to Color(0xFFA0A71B),
        R.string.crack to Color(0xFFFF29EC),
        R.string.hole to Color(0xFFE01B24),
        R.string.gate_valve to Color(0xFFC0FF41),
        R.string.not_warm to Color(0xFF003287),
        R.string.geometry to Color(0xFFD2691E),
        R.string.water to Color(0xFF00DAC7),
    )

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.select_reason),
                            textAlign = TextAlign.Start,
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "close Dialog",
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { onDismiss() },
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    for ((name, color) in listOfReasons) {
                        Button(
                            onClick = {
                                onColorSelected(color)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = color,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = stringResource(name),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Bold,
                                )

                            )
                        }
                    }
                }
            }
        }
    }
}

