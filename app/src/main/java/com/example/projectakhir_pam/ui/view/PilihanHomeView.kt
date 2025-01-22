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
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectakhir_pam.R

@Composable
fun SectionHeader() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color(0xFFc4b5c0),
            RoundedCornerShape(bottomEnd = 50.dp)
        )
    ){
        Box(){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Spacer(Modifier.padding(30.dp))
                Icon(
                    Icons.Filled.List,
                    contentDescription = " ",
                    tint = Color.Black,
                )

                // Header Title
                Spacer(Modifier.padding(8.dp))
                Text(
                    text = "UTBK ",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    ),
                    fontSize = 30.sp,
                )
                Text(
                    text = "Course ",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(Modifier.padding(16.dp))
            }
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.padding(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = " ",
                    Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .shadow(50.dp, RoundedCornerShape(100.dp))
                )
            }
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
            .background(Color.White) // Tambahkan warna latar jika diperlukan
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(
//                text = "Pilih Halaman",
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.padding(32.dp)
//            )

            Spacer(modifier = Modifier.padding(bottom = 60.dp))

            // Baris Pertama: Home Kursus & Home Siswa
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // HOME KURSUS CARD
                OutlinedCard(
                    onClick = onKursusClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        // Gambar kursus
                        Image(
                            painter = painterResource(id = R.drawable.kursus),
                            contentDescription = "Home Kursus Icon",
                            modifier = Modifier
                                .size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Teks "Home Kursus"
                        Text(
                            text = "Kursus",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                // HOME SISWA CARD
                OutlinedCard(
                    onClick = onSiswaClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                    ,
                    colors = CardDefaults.outlinedCardColors(
                        // containerColor = Color(0xFFFFCDD2) // Pink pastel color
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        // Gambar Siswa
                        Image(
                            painter = painterResource(id = R.drawable.siswa),
                            contentDescription = "Home Siswa Icon",
                            modifier = Modifier
                                .size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Teks "Siswa"
                        Text(
                            text = "Siswa",
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
                OutlinedCard(
                    onClick = onInstrukturClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        // Gambar Instruktur
                        Image(
                            painter = painterResource(id = R.drawable.pengajar),
                            contentDescription = "Home Instruktur Icon",
                            modifier = Modifier
                                .size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Teks "Instruktur"
                        Text(
                            text = "Instruktur",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                // HOME PENDAFTARAN CARD
                OutlinedCard(
                    onClick = onPendaftaranClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        // Gambar Bear
                        Image(
                            painter = painterResource(id = R.drawable.pendaftaran),
                            contentDescription = "Home Pendaftaran Icon",
                            modifier = Modifier
                                .size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Teks "Pendaftaran"
                        Text(
                            text = "Pendaftaran",
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
