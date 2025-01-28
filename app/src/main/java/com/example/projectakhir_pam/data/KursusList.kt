package com.example.projectakhir_pam.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.viewmodel.Kursus.HomeKurUiState
import com.example.projectakhir_pam.ui.viewmodel.Kursus.HomeKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel

// Objek KursusList ini bertugas untuk mengambil dan mengelola data kursus
object KursusList {

    // Fungsi DataKursus ini digunakan untuk mengambil data kursus dari ViewModel
    // Fungsi ini diberi anotasi @Composable karena digunakan untuk menghasilkan tampilan UI
    @Composable
    fun DataKursus(
        // Parameter homeKurViewModel digunakan untuk mengambil data kursus melalui ViewModel
        // ViewModel ini dikelola menggunakan factory yang disediakan oleh PenyediaViewModel
        homeKurViewModel: HomeKurViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<String, String>> { // Fungsi ini mengembalikan data dalam bentuk List pasangan (Pair)

        // Mengambil status data kursus dari ViewModel
        val kurUiState = homeKurViewModel.kurUIState

        // Mengecek status dari data kursus yang diambil
        return when (kurUiState) {

            // Jika statusnya sukses (data kursus berhasil diambil)
            is HomeKurUiState.Success -> {
                // Mengubah data kursus menjadi list pasangan (Pair) ID kursus dan nama kursus
                // Menggunakan 'map' untuk memetakan setiap kursus menjadi pair (ID kursus, Nama kursus)
                kurUiState.kursus.map {
                    it.id_kursus to it.namaKursus // Pasangan ID dan Nama kursus
                }
            }

            // Jika statusnya bukan sukses (misalnya sedang loading atau error), kembalikan list kosong
            else -> emptyList()
        }
    }
}
