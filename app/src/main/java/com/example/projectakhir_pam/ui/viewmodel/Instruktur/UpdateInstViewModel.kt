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
    var updateInstUiState by mutableStateOf(InsertInstUiState())
        private set

    private val _id_instruktur: String = checkNotNull(savedStateHandle[DestinasiUpdateInst.id_instruktur])

    init {
        viewModelScope.launch {
            updateInstUiState = instrukturRepository.getInstrukturById(_id_instruktur)
                .toUiStateInst()
        }
    }

    fun updateInsertInstState(insertUiEvent: InsertInstUiEvent){
        updateInstUiState = InsertInstUiState(insertInstUiEvent = insertUiEvent)
    }

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