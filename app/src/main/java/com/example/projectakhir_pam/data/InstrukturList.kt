package com.example.projectakhir_pam.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.HomeInstUiState
import com.example.projectakhir_pam.ui.viewmodel.Instruktur.HomeInstViewModel
import com.example.projectakhir_pam.ui.viewmodel.PenyediaViewModel

//  untuk mengambil data kursus dari ViewModel

object InstrukturList {
    @Composable
    fun DataInstruktur(
        homeInstViewModel: HomeInstViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<String, String>> {
        // Observasi state dari HomeInstViewModel
        val instUiState = homeInstViewModel.instUIState

        // Jika state sukses, peta data instruktur menjadi list nama
        return when (instUiState) {
            is HomeInstUiState.Success -> {
                instUiState.instruktur.map { it.id_instruktur to it.namaInstruktur}
            }
            else -> emptyList() // Return list kosong jika dalam kondisi lain (Loading/Error)
        }
    }
}
