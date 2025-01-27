package com.example.projectakhir_pam.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectakhir_pam.R
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi

// start home
object DestinasiStart : DestinasiNavigasi {
    override val route = "DestinasiStart"
    override val titleRes = ""
}

@Composable
fun StartHomeView(
    onMulaiButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.chocolate)), // Warna latar belakang
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo Image
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 20.dp) // Memberikan jarak di bawah logo
        )

        // Judul "UTBK Course"
        Text(
            text = "UTBK Course",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp) // Memberikan jarak di bawah judul
        )

        // Tombol Start
        OutlinedButton(
            onClick = onMulaiButton,
            shape = RectangleShape, // persegi
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(
                text = "Start",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
