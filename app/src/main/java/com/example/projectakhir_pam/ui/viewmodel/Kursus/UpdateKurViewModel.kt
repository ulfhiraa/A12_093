package com.example.projectakhir_pam.ui.viewmodel.Kursus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.repository.KursusRepository
import com.example.projectakhir_pam.ui.view.Kursus.DestinasiUpdateKur
import kotlinx.coroutines.launch

// Detail viewmodel : untuk menangani pembaruan data, termasuk logika penyimpanan dan perubahan UI state.

class UpdateKurViewModel(savedStateHandle: SavedStateHandle,
                          private val kursusRepository: KursusRepository
): ViewModel()
{
    var updateKurUiState by mutableStateOf(InsertKurUiState())
        private set

    private val _id_kursus: String = checkNotNull(savedStateHandle[DestinasiUpdateKur.id_kursus])

    init {
        viewModelScope.launch {
            updateKurUiState = kursusRepository.getKursusById(_id_kursus)
                .toUiStateKur()
        }
    }

    fun updateInsertKurState(insertUiEvent: InsertKurUiEvent){
        updateKurUiState = InsertKurUiState(insertKurUiEvent = insertUiEvent)
    }

    fun updateKur(){
        viewModelScope.launch {
            try {
                kursusRepository.updateKursus(_id_kursus,
                    updateKurUiState.insertKurUiEvent.toKur())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}