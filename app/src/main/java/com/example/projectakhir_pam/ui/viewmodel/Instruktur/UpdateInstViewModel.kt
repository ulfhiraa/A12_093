package com.example.projectakhir_pam.ui.viewmodel.Instruktur

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.repository.InstrukturRepository
import com.example.projectakhir_pam.ui.view.Instruktur.DestinasiUpdateInst
import kotlinx.coroutines.launch


// Detail viewmodel : untuk menangani pembaruan data, termasuk logika penyimpanan dan perubahan UI state.

class UpdateInstViewModel(savedStateHandle: SavedStateHandle,
                         private val instrukturRepository: InstrukturRepository
): ViewModel()
{
    // Menyimpan data instruktur yang sedang diperbarui
    var updateInstUiState by mutableStateOf(InsertInstUiState())
        private set

    private val _id_instruktur: String = checkNotNull(savedStateHandle[DestinasiUpdateInst.id_instruktur])

    // Mengambil data instruktur berdasarkan ID dan mengubahnya untuk ditampilkan di UI
    init {
        viewModelScope.launch {
            updateInstUiState = instrukturRepository.getInstrukturById(_id_instruktur)
                .toUiStateInst()
        }
    }

    // Mengubah status UI berdasarkan input yang diterima dari form
    fun updateInsertInstState(insertUiEvent: InsertInstUiEvent){
        updateInstUiState = InsertInstUiState(insertInstUiEvent = insertUiEvent)
    }

    // Menyimpan perubahan instruktur ke repository ( database atau server)
    fun updateInst(){
        viewModelScope.launch {
            try {
                instrukturRepository.updateInstruktur(_id_instruktur,
                updateInstUiState.insertInstUiEvent.toInst())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}