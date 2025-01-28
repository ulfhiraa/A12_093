package com.example.projectakhir_pam.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisUiState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisViewModel

// Objek SiswaList ini bertugas untuk mengambil dan mengelola data siswa
object SiswaList {

    // Fungsi DataSiswa ini digunakan untuk mengambil data siswa dari ViewModel
    // Fungsi ini diberi anotasi @Composable karena berfungsi untuk menghasilkan UI
    @Composable
    fun DataSiswa(
        // Parameter homeSisViewModel akan menyediakan data siswa melalui ViewModel
        // ViewModel ini dibuat menggunakan factory yang disediakan oleh PenyediaViewModel
        homeSisViewModel: HomeSisViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<String, String>> { // Fungsi ini mengembalikan List pasangan (Pair)

        // Mengambil data dari HomeSisViewModel yang berisi status data siswa
        val sisUiState = homeSisViewModel.sisUIState

        // Mengecek kondisi dari sisUiState (apakah data siswa berhasil diambil atau belum)
        return when (sisUiState) {

            // Jika statusnya berhasil mengambil data (Success)
            is HomeSisUiState.Success -> {
                // Mengubah data siswa menjadi list berisi pasangan ID dan nama siswa
                // 'map' digunakan untuk mengubah setiap elemen menjadi pair (ID Siswa, Nama Siswa)
                sisUiState.siswa.map {
                    // Mengembalikan pasangan ID dan Nama siswa
                    it.id_siswa to it.namaSiswa
                }
            }

            // Jika statusnya bukan sukses (misalnya sedang loading atau error), kembalikan list kosong
            else -> emptyList()
        }
    }
}
