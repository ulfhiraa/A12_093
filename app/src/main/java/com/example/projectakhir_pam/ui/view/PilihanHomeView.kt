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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavController
import com.example.projectakhir_pam.R
import com.example.projectakhir_pam.ui.navigasi.DestinasiNavigasi

// main home
object DestinasiHome : DestinasiNavigasi {
    override val route = "DestinasiHome"
    override val titleRes = "Halaman Utama"
}

@Composable
fun SectionHeader(
    canNavigateBack: Boolean, // Menambahkan parameter canNavigateBack
    navigateUp: () -> Unit // Menambahkan parameter untuk navigateUp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF291010), // Warna latar belakang header
                shape = RoundedCornerShape(bottomEnd = 100.dp) // Sudut rounded pada bagian bawah
            )
            .padding(bottom = 10.dp) // Padding bawah
    ) {
        // Baris pertama untuk tombol kembali dan logo di sebelah kanan
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp), // Memberikan padding atas dan kiri
            horizontalArrangement = Arrangement.SpaceBetween, // Menyebar ke kiri dan kanan
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol Kembali
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFFf9f3f3) // warna button back
                    )
                }
            }

            // Logo di sebelah kanan
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(130.dp)
                    .padding(top = 10.dp) // Memberikan jarak atas
                    .padding(end = 16.dp) // Padding kanan untuk logo
            )
        }

        Spacer(modifier = Modifier.height(70.dp)) // Menambahkan jarak setelah logo dan tombol kembali

        // Baris kedua untuk teks UTBK & Course
        Column(
            modifier = Modifier
                .padding(start = 32.dp) // Memberikan jarak setelah tombol
                .padding(top = 40.dp) // Memberikan jarak atas
                .fillMaxWidth(), // Memastikan teks memenuhi lebar
            horizontalAlignment = Alignment.Start // Rata kiri untuk teks
        ) {
            Spacer(modifier = Modifier.height(70.dp)) // Menambahkan jarak setelah logo dan tombol kembali

            // Menampilkan teks "UTBK"
            Text(
                text = "UTBK ",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color(0xFFf9f3f3) // Warna teks lebih gelap
                )
            )

            // Menampilkan teks "Course"
            Text(
                text = "Course ",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color(0xFFf9f3f3) // Warna teks lebih gelap
                )
            )

            Spacer(modifier = Modifier.height(10.dp)) // Jarak antar teks

            // Menampilkan deskripsi
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
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PilihanHomeView(
    onKursusClick: () -> Unit,
    onSiswaClick: () -> Unit,
    onInstrukturClick: () -> Unit,
    onPendaftaranClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit, // This is the function passed for back navigation
    navController: NavController // Adding NavController to navigate back
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF3c2121))
    ) {
        // Header Section
        SectionHeader(
            canNavigateBack = true, // Tombol kembali aktif
            navigateUp = navigateBack // Fungsi untuk kembali
        )

        // Spacer untuk memberikan jarak antara header dan card
        Spacer(modifier = Modifier.height(16.dp))

        // Card Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

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
