package com.example.projectakhir_pam.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.viewmodel.Kursus.HomeKurUiState
import com.example.projectakhir_pam.ui.viewmodel.Kursus.HomeKurViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel

//  untuk mengambil data kursus dari ViewModel
object KursusList {
    @Composable
    fun DataKursus(
        homeKurViewModel: HomeKurViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<String, String>> {
        // Observasi state dari HomeKurViewModel
        val kurUiState = homeKurViewModel.kurUIState

        // Jika state sukses, peta data instruktur menjadi list nama
        return when (kurUiState) {
            is HomeKurUiState.Success -> {
                kurUiState.kursus.map { it.id_kursus to it.namaKursus }
            }
            else -> emptyList() // Return list kosong jika dalam kondisi lain (Loading/Error)
        }
    }
}