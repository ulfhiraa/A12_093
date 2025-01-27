package com.example.projectakhir_pam.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectakhir_pam.R
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi

// main home
object DestinasiHome : DestinasiNavigasi {
    override val route = "DestinasiHome"
    override val titleRes = "Halaman Utama"
}

@Composable
fun SectionHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF291010

                ), // Warna pink pastel lembut
                //color = Color(0xFFf7f0f6), // Warna pink pastel lembut
                shape = RoundedCornerShape(bottomEnd = 100.dp)
            )
            .padding(bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(85.dp))
                Text(
                    text = "UTBK ",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        color = Color(0xFFf9f3f3) // Warna teks lebih gelap
                    )
                )
                Text(
                    text = "Course ",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        color = Color(0xFFf9f3f3) // Warna teks lebih gelap
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Ubur-ubur ikan lele, " +
                            "\nSukses di PTN impian lee!",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Light,
                        fontSize = 20.sp,
                        color = Color(0xFF7C7C7C) // Warna teks deskripsi lebih terang
                    )
                )
            }

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PilihanHomeView(
    onKursusClick: () -> Unit,
    onSiswaClick: () -> Unit,
    onInstrukturClick: () -> Unit,
    onPendaftaranClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF3c2121))
    ) {
        // Header Section
        SectionHeader()

        // Spacer untuk memberikan jarak antara header dan card
        Spacer(modifier = Modifier.height(16.dp))

        // Card Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
//            Text(
//                text = "안녕 하세요, \n" +
//                        "Selamat Datang! >_<",
//                style = MaterialTheme.typography.headlineSmall,
//                modifier = Modifier.padding(5.dp)
//            )

//            Text(
//                text = "                 Raih skor memuaskan" +
//                        "\ndan masuk PTN impianmu bersama kami \uD83D\uDCAA",
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.padding(5.dp)
//            )

            Spacer(modifier = Modifier.padding(bottom = 40.dp))

            // Baris Pertama: Home Kursus & Home Siswa
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // HOME KURSUS CARD
                ElevatedCard(
                    onClick = onKursusClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .shadow(12.dp),
                    colors = CardDefaults.outlinedCardColors(
                        //containerColor = Color(0xFFf3ebf3) // Pink pastel color
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        // Gambar kursus
                        Image(
                            painter = painterResource(id = R.drawable.kursus),
                            contentDescription = "Home Kursus Icon",
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Teks "Home Kursus"
                        Text(
                            text = "K u r s u s",
                            style = MaterialTheme.typography.titleMedium.copy
                                (fontWeight = FontWeight.ExtraBold),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                // HOME SISWA CARD
                ElevatedCard(
                    onClick = onSiswaClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .shadow(12.dp)
                    ,
                    colors = CardDefaults.outlinedCardColors(
                         //containerColor = Color(0xFFf3ebf3) // Pink pastel color
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        // Gambar Siswa
                        Image(
                            painter = painterResource(id = R.drawable.siswa),
                            contentDescription = "Home Siswa Icon",
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Teks "Siswa"
                        Text(
                            text = "S i s w a",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            // Baris Kedua: HOME INSTRUKTUR & HOME PENDAFTARAN
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // HOME INSTRUKTUR CARD
                ElevatedCard(
                    onClick = onInstrukturClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .shadow(12.dp),
                    colors = CardDefaults.outlinedCardColors(
                            //containerColor = Color(0xFFf3ebf3) // Pink pastel color
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        // Gambar Instruktur
                        Image(
                            painter = painterResource(id = R.drawable.pengajar),
                            contentDescription = "Home Instruktur Icon",
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Teks "Instruktur"
                        Text(
                            text = "I n s t r u k t u r",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                // HOME PENDAFTARAN CARD
                ElevatedCard(
                    onClick = onPendaftaranClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .shadow(12.dp),
                    colors = CardDefaults.outlinedCardColors(
                        //containerColor = Color(0xFFf3ebf3) // Pink pastel color
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        // Gambar Bear
                        Image(
                            painter = painterResource(id = R.drawable.pendaftaran),
                            contentDescription = "Home Pendaftaran Icon",
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Teks "Pendaftaran"
                        Text(
                            text = "P e n d a f t a r a n",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}
