package com.example.projectakhir_pam.ui.viewmodel.Pendaftaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir_pam.model.Pendaftaran
import com.example.projectakhir_pam.repository.PendaftaranRepository
import com.example.projectakhir_pam.ui.view.Pendaftaran.DestinasiDetailPend
import kotlinx.coroutines.launch

// Detail viewmodel : untuk mengelola pengambilan dan penghapusan data pendaftaran berdasarkan id dan pembaruan UI State

class DetailPendViewModel(savedStateHandle: SavedStateHandle,
                         private val pendaftaranRepository: PendaftaranRepository
) : ViewModel()
{
    private val id_pendaftaran: String = checkNotNull(savedStateHandle[DestinasiDetailPend.id_pendaftaran])

    var detailPendUiState: DetailPendUiState by mutableStateOf(DetailPendUiState())
        private set

    init {
        getPendaftaranById()
    }

    fun getPendaftaranById() {
        viewModelScope.launch {
            detailPendUiState = DetailPendUiState(isLoading = true)
            try {
                val result = pendaftaranRepository.getPendaftaranById(id_pendaftaran)
                detailPendUiState = DetailPendUiState(
                    detailPendUiEvent = result.toDetailPendUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailPendUiState = DetailPendUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown"
                )
            }
        }
    }
}

data class DetailPendUiState(
    val detailPendUiEvent: InsertPendUiEvent = InsertPendUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailPendUiEvent == InsertPendUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailPendUiEvent != InsertPendUiEvent()
}

fun Pendaftaran.toDetailPendUiEvent(): InsertPendUiEvent {
    return InsertPendUiEvent(
        id_pendaftaran = id_pendaftaran,
        id_siswa = id_siswa,
        id_kursus = id_kursus,
        tglDaftar = tglDaftar,
        status = status
    )
}