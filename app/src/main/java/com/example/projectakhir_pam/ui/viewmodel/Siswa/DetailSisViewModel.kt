package com.example.projectakhir_pam.ui.viewmodel.Siswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Siswa
import com.example.projectakhir_pam.repository.SiswaRepository
import com.example.projectakhir_pam.ui.view.Siswa.DestinasiDetailSis
import kotlinx.coroutines.launch

// Detail viewmodel : untuk mengelola pengambilan dan penghapusan data siswa berdasarkan id dan pembaruan UI State

class DetailSisViewModel(savedStateHandle: SavedStateHandle,
                      private val siswaRepository: SiswaRepository) : ViewModel()
{
    private val id_siswa: String = checkNotNull(savedStateHandle[DestinasiDetailSis.id_siswa])

    var detailSisUiState: DetailSisUiState by mutableStateOf(DetailSisUiState())
        private set

    init {
        getSiswaById()
    }

    fun getSiswaById() {
        viewModelScope.launch {
            detailSisUiState = DetailSisUiState(isLoading = true)
            try {
                val result = siswaRepository.getSiswaById(id_siswa)
                detailSisUiState = DetailSisUiState(
                    detailSisUiEvent = result.toDetailSisUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailSisUiState = DetailSisUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown"
                )
            }
        }
    }
}

data class DetailSisUiState(
    val detailSisUiEvent: InsertSisUiEvent = InsertSisUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailSisUiEvent == InsertSisUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailSisUiEvent != InsertSisUiEvent()
}

fun Siswa.toDetailSisUiEvent(): InsertSisUiEvent {
    return InsertSisUiEvent(
        id_siswa = id_siswa,
        namaSiswa = namaSiswa,
        email = email,
        noTelpSiswa = noTelpSiswa
    )
}