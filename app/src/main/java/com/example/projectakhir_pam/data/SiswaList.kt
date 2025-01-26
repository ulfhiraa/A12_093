package com.example.projectakhir_pam.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisUiState
import com.example.projectakhir_pam.ui.viewmodel.Siswa.HomeSisViewModel

//  untuk mengambil data kursus dari ViewModel
object SiswaList {
@Composable
    fun DataSiswa(
        homeSisViewModel: HomeSisViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<String, String>> {
        // Observasi state dari HomeSisViewModel
        val sisUiState = homeSisViewModel.sisUIState

        // Jika state sukses, peta data instruktur menjadi list nama
        return when (sisUiState) {
                is HomeSisUiState.Success -> {
                sisUiState.siswa.map { it.id_siswa to it.namaSiswa}
            }
            else -> emptyList() // Return list kosong jika dalam kondisi lain (Loading/Error)
        }
    }
}
