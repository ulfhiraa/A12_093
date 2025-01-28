package com.example.projectakhir_pam.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.HomeInstUiState
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.HomeInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel

// Objek InstrukturList ini berfungsi untuk mengelola dan mengambil data instruktur
object InstrukturList {

    // Fungsi DataInstruktur ini digunakan untuk mengambil data instruktur
    // Fungsi ini adalah komponen UI yang menggunakan anotasi @Composable
    @Composable
    fun DataInstruktur(
        // Menggunakan HomeInstViewModel untuk mengambil data instruktur
        // ViewModel ini digunakan untuk mengelola data dan logika aplikasi
        homeInstViewModel: HomeInstViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<String, String>> { // Fungsi ini mengembalikan data dalam bentuk List pasangan (Pair)

        // Mengambil data dari state HomeInstViewModel
        // instUIState berisi status data instruktur apakah sudah berhasil diambil atau belum
        val instUiState = homeInstViewModel.instUIState

        // Mengecek status data instruktur
        return when (instUiState) {
            // Jika statusnya sukses (data instruktur berhasil diambil)
            is HomeInstUiState.Success -> {
                // Mengubah data instruktur menjadi List berisi pasangan (Pair) ID dan Nama instruktur
                // 'map' digunakan untuk mengubah setiap instruktur menjadi pair (id_instruktur, namaInstruktur)
                instUiState.instruktur.map {
                    // Mengembalikan pasangan ID dan Nama instruktur
                    it.id_instruktur to it.namaInstruktur
                }
            }
            // Jika statusnya bukan sukses (misalnya loading atau error), kembalikan list kosong
            else -> emptyList()
        }
    }
}
