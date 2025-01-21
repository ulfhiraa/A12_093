package com.example.projectakhir_pam.ui.viewmodel.Siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.repository.SiswaRepository
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiUpdate
import kotlinx.coroutines.launch

// Detail viewmodel : untuk menangani pembaruan data, termasuk logika penyimpanan dan perubahan UI state.

class UpdateSisViewModel(savedStateHandle: SavedStateHandle,
                      private val siswaRepository: SiswaRepository): ViewModel()
{
    var updateSisUiState by mutableStateOf(InsertSisUiState())
        private set

    private val _id_siswa: String = checkNotNull(savedStateHandle[DestinasiUpdate.id_siswa])

    init {
        viewModelScope.launch {
            updateSisUiState = siswaRepository.getSiswaById(_id_siswa)
                .toUiStateSis()
        }
    }

    fun updateInsertSisState(insertUiEvent: InsertSisUiEvent){
        updateSisUiState = InsertSisUiState(insertSisUiEvent = insertUiEvent)
    }

    fun updateMhs(){
        viewModelScope.launch {
            try {
                siswaRepository.updateSiswa(_id_siswa, updateSisUiState.insertSisUiEvent.toSis())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}