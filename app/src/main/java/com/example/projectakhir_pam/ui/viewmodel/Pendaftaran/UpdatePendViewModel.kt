package com.example.projectakhir_pam.ui.viewmodel.Pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.repository.PendaftaranRepository
import com.example.projectakhir_pam.ui.view.Pendaftaran.DestinasiUpdatePend
import kotlinx.coroutines.launch

// Detail viewmodel : untuk menangani pembaruan data, termasuk logika penyimpanan dan perubahan UI state.

class UpdatePendViewModel(savedStateHandle: SavedStateHandle,
                         private val pendaftaranRepository: PendaftaranRepository
): ViewModel()
{
    var updatePendUiState by mutableStateOf(InsertPendUiState())
        private set

    private val _id_pendaftaran: String = checkNotNull(savedStateHandle[DestinasiUpdatePend.id_pendaftaran])

    init {
        viewModelScope.launch {
            updatePendUiState = pendaftaranRepository.getPendaftaranById(_id_pendaftaran)
                .toUiStatePend()
        }
    }

    fun updateInsertPendState(insertUiEvent: InsertPendUiEvent){
        updatePendUiState = InsertPendUiState(insertPendUiEvent = insertUiEvent)
    }

    fun updatePend(){
        viewModelScope.launch {
            try {
                pendaftaranRepository.updatePendaftaran(_id_pendaftaran,
                    updatePendUiState.insertPendUiEvent.toPend())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}